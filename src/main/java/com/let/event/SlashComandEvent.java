package com.let.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.let.client.NexonApiClient;
import com.let.domain.MaplePartyScheduleVO;
import com.let.service.MapleDistributionService;
import com.let.service.MapleDutyCheckService;
import com.let.service.MaplePartyScheduleService;
import com.let.service.MapleUtilService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * packageName    : com.let.event
 * fileName       : SlashComandEvent
 * author         : jun
 * date           : 25. 12. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 10.        jun       최초 생성
 */
@Component
@RequiredArgsConstructor
public class SlashComandEvent extends ListenerAdapter {

    @Autowired
    private MapleDutyCheckService mapleDutyCheckService;
    @Autowired
    private MapleUtilService mapleUtilService;
    @Autowired
    private MapleDistributionService mapleDistributionService;
    @Autowired
    private MaplePartyScheduleService maplePartyScheduleService;
    @Autowired
    private NexonApiClient nexonApiClient;


    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandDatas = new ArrayList<>();

        //서버 옵션
        LinkedHashMap<String,String> servers = new LinkedHashMap<>();
        servers.put("베라","BERRA");
        servers.put("스카니아","SCANIA");
        servers.put("루나","LUNA");
        servers.put("크로아","CROA");
        OptionData serverOption = this.mapleUtilService.setOptionData(OptionType.STRING,"서버","서버를 선택하세요",true,servers);

        commandDatas.add(
                Commands.slash("관세계산기", "아이템의 관세를 계산합니다. (억 단위) 메포시세 미 입력 시 가장 최근에 검색된 값 사용")
                        .addOptions(
                                new OptionData(OptionType.INTEGER, "아이템금액", "100", true),
                                serverOption,
                                new OptionData(OptionType.INTEGER, "메포시세", "2165", false)
                        )

        );

        //분배금 계산을 위한 옵션
        LinkedHashMap<String,String> peopleCount = new LinkedHashMap<>();
        peopleCount.put("2","2");
        peopleCount.put("3","3");
        peopleCount.put("4","4");
        peopleCount.put("5","5");
        peopleCount.put("6","6");
        OptionData peopleOption = this.mapleUtilService.setOptionData(OptionType.STRING,"분배인원","분배 인원을 설정 해주세요.",true,peopleCount);

        //수수료
        LinkedHashMap<String,String> charge = new LinkedHashMap<>();
        charge.put("5%","5");
        charge.put("3%","3");
        OptionData chargeOption = this.mapleUtilService.setOptionData(OptionType.STRING,"수수료","수수료를 설정 해주세요.",true,charge);

        //분배 구분
        LinkedHashMap<String,String> distribution = new LinkedHashMap<>();
        distribution.put("균등","균등");
        distribution.put("자율","자율");
        OptionData distributionOption = this.mapleUtilService.setOptionData(OptionType.STRING,"분배구분","균등분배, 자율분배",true,distribution);

        //배율 50:20:30
        OptionData ratioOption = this.mapleUtilService.setOptionData(OptionType.STRING,"배율","( 총비율 100, x:y:z 형식, 아이템 판매자(1번) 수수료 제외 ) 자율 분배일 경우 각 인원의 기여도  ex) 50:30:20",false,null);

        commandDatas.add(
                    Commands.slash("분배금계산기", "분배금액(억 ")
                            .addOptions(
                                    new OptionData(OptionType.INTEGER, "아이템금액", "100 (억 단위)", true),
                                    chargeOption,
                                    peopleOption,
                                    distributionOption,
                                    ratioOption
                            )

        );

        //보스파티 일정등록
        commandDatas.add(
                Commands.slash("일정등록", "파티 일정을 등록합니다.")
                        .addOption(OptionType.STRING, "일정명", "예: 검세칼카", true)
                        .addOption(OptionType.STRING, "날짜", "예: 2025.12.25 (yyyy.MM.dd)", true)
                        .addOption(OptionType.STRING, "시간", "예: 07:00 (HH:mm)", true)
        );

        commandDatas.add(
                Commands.slash("캐릭터조회", "캐리터명으로 캐릭터의 정보를 조회합니다.")
                        .addOption(OptionType.STRING, "캐릭터명", "쓰낵", true)
        );

        event.getGuild().updateCommands().addCommands(commandDatas).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        // 문자열 채널 ID
        String channelId = event.getChannel().getId();
        //고담 , 봇테 채널
        if(!channelId.equals("1450034042517852182") && !channelId.equals("1448173918283108469")) return;

        String eventName = event.getName();


        switch (eventName){
            case "관세계산기":
                //아이템 금액
                int itemPay = Objects.requireNonNull(event.getOption("아이템금액")).getAsInt();

                var mapleOption = event.getOption("메포시세");

                // 메포 시세 가지고 있는지 디비에서 조회
                // 서버 값 읽기 (SCANIA / LUNA / CROA / BERRA...)
                String server = Objects.requireNonNull(event.getOption("서버")).getAsString();

                // 메포시세를 입력했는지 체크
                Integer maplePoint = null;
                try {

                    maplePoint = this.mapleDutyCheckService.checkCommandMaplePoint(mapleOption,server);

                    if(maplePoint == null)  event.reply("수집된 메이플 포인트 시세가 없습니다. 메이플 포인트 시세를 입력 해 다시 시도 해주세요.").queue();

                }catch (Exception e){

                    event.reply("""
                            메이플 포인트 시세 서버와의 연결에 문제가 있어 조회할 수 없습니다.
                            잠시 후 다시 시도해 주세요.
                            """)
                            .setEphemeral(true)
                            .queue();
                    return;

                }

                //바꿔야할 메소(억단위)
                int myPayMeso = (itemPay / 10);
                //충전해야할 메이플 포인트 금액
                int myPayPoint =  myPayMeso * maplePoint;

                //현 서버에 동일한 금액의 아이템 가격
                int myServerItemAmt = itemPay + myPayMeso;


                // 한 메세지에 세줄로 전달
                event.reply("""
                        입력 받은 메포 시세 (미 입력 시 최신 메포시세) : %d원
                        입력 받은 아이템 금액 : %d억
                        충전 해야할 메이플 포인트 : %d원
                        충전에 사용될 메소 (억단위) : %d억
                        내 서버에서 동일한 금액의 아이템 금액 : %d억
                        """.formatted(maplePoint,itemPay,myPayPoint, myPayMeso, myServerItemAmt)
                                ).queue();
                break;

            case "분배금계산기" :

                String distributionOption = Objects.requireNonNull(event.getOption("분배구분")).getAsString();

                int inputPay = Objects.requireNonNull(event.getOption("아이템금액")).getAsInt();
                int peopleCount = Objects.requireNonNull(event.getOption("분배인원")).getAsInt();
                int feePercent = Objects.requireNonNull(event.getOption("수수료")).getAsInt();

                //수수료 ex) 0.95
                double fee = (double) (100 - feePercent) / 100;

                //아이템 금액 억단위 추가 (수수료 노 제외)
                long itemAmt = (inputPay * 100000000L);

                //수수료 제외 된 금액
                double afterFeeAmt = inputPay * fee;

                switch (distributionOption){
                    case "균등" :


                        //최종 인당 분배금액
                        // 아이템 금액/(1+(1/fee)*(파티원-1))
                        BigDecimal resultAmt = BigDecimal.valueOf(itemAmt / (1+(1/fee) * (peopleCount-1) ));


                        //입력금액(수수료 제외), 분배인원, 분배금(교환창에 올릴 금액)
                        event.reply("""
                        %s 분배
                        
                        입력 받은 분배 금액(수수료 제외) : %.1f억
                        
                        분배 인원 : %d명
                        
                        분배금(교환창에 올릴 메소) : %,.0f메소
                        """.formatted(distributionOption,afterFeeAmt,peopleCount, resultAmt)
                        ).queue();

                        break;
                    case "자율" :

                        var distributionPercentOption = event.getOption("배율");

                        // 배율 유효성 검증
                        JSONObject autonomousDistributionValidationObj =  this.mapleDistributionService.autonomousDistributionValidationCheck(distributionPercentOption);

                        if(!autonomousDistributionValidationObj.getBoolean("isOk")){
                            event.reply(autonomousDistributionValidationObj.getString("msg"))
                                    .setEphemeral(true)
                                    .queue();
                        }
                        //사용자가 입력한 배율 50:30:20
                        String[] parts = (String[]) autonomousDistributionValidationObj.get("parts");

                        //파티원 및 사용자 분배금리스트 [0] = 교환창에 올릴 메소(세전), [1] = 실 수령 메소(세후)
                        List<BigDecimal[]> shareList = this.mapleDistributionService.getShareList(parts,itemAmt,fee);

                        StringBuilder sb = new StringBuilder();

                        sb.append("""
                        %s 분배
                        
                        입력 받은 분배 금액(수수료 제외) : %.1f억
                        분배 인원 : %d명
                        
                        아이템 판매자 (수수료제외)
                        """.formatted(distributionOption, afterFeeAmt, peopleCount));

                        //역순으로 진행하는 이유는 파티원 분배금을 지급 후 남은 금액을 아이템판매자(1번)이 가져가기 때문.
                        int num= 1;
                        for (int i = shareList.size()-1; i >=0; i--) {

                            BigDecimal gross = shareList.get(i)[0]; // 교환창 메소
                            BigDecimal net   = shareList.get(i)[1]; // 실 수령 메소
                            sb.append(String.format(
                                    "%d번 분배금 : %,.0f메소%n",
                                    num,
                                    gross
                            ));

                            sb.append(String.format(
                                    "%d번 실 수령금 : %,.0f메소%n%n" +
                                            "",
                                    num,
                                    net
                            ));

                            num++;
                        }

                        event.reply(sb.toString()).queue();

                        break;

                }
            case "일정등록" :

                // 패턴은 문자열 형식이랑 정확히 맞춰야 함
                String inputDate = Objects.requireNonNull(event.getOption("날짜")).getAsString();
                String inputTime  = Objects.requireNonNull(event.getOption("시간")).getAsString();
                String inputTitle  = Objects.requireNonNull(event.getOption("일정명")).getAsString();

                //유효성 체크
                boolean checkDateTime = this.mapleUtilService.checkDateTime(inputDate,inputTime);
                if(!checkDateTime) {
                    event.reply("날짜 및 시간 형식이 맞지 않습니다.")
                            .setEphemeral(true)
                            .queue();
                    break;
                }

                //날짜 및 시간 파싱
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                Date bossDate;
                try {
                    bossDate = sdf.parse(inputDate);
                } catch (ParseException e) {
                    event.reply("보스 일정 날짜 파싱 중 오류가 발생했습니다.")
                            .setEphemeral(true)
                            .queue();
                    throw new RuntimeException(e);
                }
                Time bossTime = Time.valueOf(inputTime+":00");

                //일정 등록 -> 키값은 vo 안에 있음
                MaplePartyScheduleVO parytVO = new MaplePartyScheduleVO(bossDate,bossTime,inputTitle,channelId);
                int insertParyt = this.maplePartyScheduleService.insertMapleParytSchedule(parytVO);

                if(insertParyt <= 0){
                    event.reply("일정을 저장 중 오류가 발생했습니다.. 잠시후 다시시도 해주세요.")
                            .setEphemeral(true)
                            .queue();
                    break;
                }

                // 유저 선택 셀렉트박스 생성
                EntitySelectMenu menu = EntitySelectMenu
                        .create(String.valueOf(parytVO.getId()), EntitySelectMenu.SelectTarget.USER)
                        .setPlaceholder("일정을 등록할 멤버를 선택하세요 (최소 2명 최대 6명)")
                        .setRequiredRange(2, 6) // 최소 2명, 최대 6명 선택
                        .build();
                event.reply("일정을 등록할 멤버를 선택해 주세요. (최소 2명 최대 6명) ")
                        .addComponents(ActionRow.of(menu))
                        .setEphemeral(true) // 선택 UI는 본인만 보이게
                        .queue();

                break;
            case "캐릭터조회" :
                //오늘날
                LocalDate now = LocalDate.now();
                String nowDateFormat = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                String inputName = Objects.requireNonNull(event.getOption("캐릭터명")).getAsString();

                //캐릭터의 키 값 조회
                //JDA 의 경우는 응답을 빠르게 처리해야해서 비동기로 처리 할 수 있도록 추가
                event.deferReply(false).queue(hook -> {
                    //캐릭터 고유 키값 체크
                    nexonApiClient.getJson("/maplestory/v1/id", Map.of("character_name", inputName))
                            .map(json -> json.path("ocid").asText(""))
//                            .flatMap(ocid -> {
//
//                                //키값 유효성 검증
//                                if (ocid.isBlank()) {
//                                    return reactor.core.publisher.Mono.error(new IllegalStateException("ocid가 비어있음"));
//                                }
//                                // ocid로 다음 API 호출
//                                return nexonApiClient.getJson("/maplestory/v1/character/basic", Map.of("ocid", ocid));
//                            })
//                            .subscribe(
//                                    basicJson -> {
//                                        //조회한 캐릭터 정보
//                                        String characterName = basicJson.path("character_name").asText("");
//                                        int level = basicJson.path("character_level").asInt(0);
//                                        String characterClass = basicJson.path("character_class").asText("");
//                                        String characterExpRate = basicJson.path("character_exp_rate").asText("");
//                                        String characterGuildName = basicJson.path("character_guild_name").asText("");
//
//                                        //캐릭터 이미지
//                                        var eb = new EmbedBuilder()
//                                                .setTitle("캐릭터 이미지")
//                                                .setImage(basicJson.path("character_image").asText("")); // 공개로 접근 가능한 이미지 URL
//
//                                        String resultMsg = """
//                                            조회일자 : %s
//
//                                            캐릭터 명 : %s
//                                            레벨 : %s
//                                            직업 : %s
//                                            경험치 : %s
//                                            길드 : %s
//
//                                            """.formatted(nowDateFormat,characterName,level,characterClass, characterExpRate, characterGuildName);
//
//                                        hook.editOriginal(resultMsg)
//                                                .setEmbeds(eb.build())
//                                                .queue();
//                                    },
//                                    err -> hook.editOriginal("API 호출 실패: " + err.getMessage()).queue()
//                            );
                            .flatMap(ocid -> reactor.core.publisher.Mono.zip(
                                    nexonApiClient.getJson("/maplestory/v1/character/basic", Map.of("ocid", ocid)),
                                    nexonApiClient.getJson("/maplestory/v1/character/stat", Map.of("ocid", ocid)),
                                    nexonApiClient.getJson("/maplestory/v1/character/item-equipment", Map.of("ocid", ocid))
                            ))
                            .subscribe(tuple -> {

                                JsonNode basic = tuple.getT1();

                                //캐릭터 기본 정보
                                String characterName = basic.path("character_name").asText("");
                                int level = basic.path("character_level").asInt(0);
                                String characterClass = basic.path("character_class").asText("");
                                String characterExpRate = basic.path("character_exp_rate").asText("");
                                String characterGuildName = basic.path("character_guild_name").asText("");
                                //캐릭터 이미지
                                var eb = new EmbedBuilder()
                                        .setTitle("캐릭터 이미지")
                                        .setImage(basic.path("character_image").asText("")); // 공개로 접근 가능한 이미지 URL
                                JsonNode stat  = tuple.getT2();
                                JsonNode equip = tuple.getT3();


                                System.out.println(stat);
                                System.out.println("---");
                                System.out.println(equip);

                                        String resultMsg = """
                                            조회일자 : %s

                                            캐릭터 명 : %s
                                            레벨 : %s
                                            직업 : %s
                                            경험치 : %s
                                            길드 : %s

                                            """.formatted(nowDateFormat,characterName,level,characterClass, characterExpRate, characterGuildName);

                                        hook.editOriginal(resultMsg)
                                                .setEmbeds(eb.build())
                                                .queue();
                            }, err -> hook.editOriginal("실패: " + err.getMessage()).queue());
                });

                break;
        }

    }

}
