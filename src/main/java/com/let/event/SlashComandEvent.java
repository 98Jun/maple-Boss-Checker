package com.let.event;

import com.let.domain.MaplePointDutyCheckVO;
import com.let.service.MapleDutyCheckService;
import com.let.service.impl.MapleDutyCheckMapper;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Value("${maple.api.key}")
    private String mapleApiKey;

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandDatas = new ArrayList<>();

        //서버 옵션
        OptionData serverOption = new OptionData(
                OptionType.STRING,
                "서버",
                "서버를 선택하세요",
                true // required
        )
                .addChoice("베라", "BERRA")
                .addChoice("스카니아", "SCANIA")
                .addChoice("루나", "LUNA")
                .addChoice("크로아", "CROA");


        commandDatas.add(
                Commands.slash("관세계산기", "아이템의 관세를 계산합니다. (억 단위) 메포시세 미 입력 시 가장 최근에 검색된 값 사용")
                        .addOptions(
                                new OptionData(OptionType.INTEGER, "아이템금액", "100", true),
                                serverOption,
                                new OptionData(OptionType.INTEGER, "메포시세", "2165", false)
                        )

        );

        //분배금 계산을 위한 옵션
        OptionData pepleOption = new OptionData(
                OptionType.STRING,
                "분배인원",
                "분배 인원을 설정 해주세요",
                true // required
        )
                .addChoice("2", "2")
                .addChoice("3", "3")
                .addChoice("4", "4")
                .addChoice("5", "5")
                .addChoice("6", "6");

        OptionData chargeOption = new OptionData(
                OptionType.STRING,
                "수수료",
                "수수료를 설정 해주세요",
                true // required
        )
                .addChoice("5%", "5")
                .addChoice("3%", "3");

        OptionData distributionOption = new OptionData(
                OptionType.STRING,
                "분배구분",
                "균등분배,자율분배",
                true // required
        )
                .addChoice("균등", "균등")
                .addChoice("자율", "자율");

        OptionData ratioOption = new OptionData(
                OptionType.STRING,
                "배율",
                "( 총비율 100, x:y:z 형식, 아이템 판매자(1번) 수수료 제외 ) 자율 분배일 경우 각 인원의 기여도  ex) 50:30:20",
                false // ✅ UI 상으로는 optional
        );

        commandDatas.add(
                    Commands.slash("분배금계산기", "분배금액(억 ")
                            .addOptions(
                                    new OptionData(OptionType.INTEGER, "아이템금액", "100 (억 단위)", true),
                                    chargeOption,
                                    pepleOption,
                                    distributionOption,
                                    ratioOption
                            )

        );

        event.getGuild().updateCommands().addCommands(commandDatas).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){

        if(!event.getChannel().getId().equals("1448173918283108469")) return;

        String eventName = event.getName();


        switch (eventName){
            case "관세계산기":
                //아이템 금액
                int itemPay = Objects.requireNonNull(event.getOption("아이템금액")).getAsInt();

                var mapleOption = event.getOption("메포시세");

                // 메포 시세 가지고 있는지 디비에서 조회
                // 서버 값 읽기 (SCANIA / LUNA / CROA / BERRA...)
                String server = Objects.requireNonNull(event.getOption("서버")).getAsString();

                // 👉 메포시세를 입력했는지 체크
                Integer maplePoint;


                if (mapleOption != null) {
                    // 1) 사용자가 메포시세를 직접 입력한 경우 → 그 값 사용
                    maplePoint = mapleOption.getAsInt();
                    this.mapleDutyCheckService.insertMaplePointHistory(new MaplePointDutyCheckVO(maplePoint,server));
                } else {
                    // 2) 입력 안 했으면 → DB에서 가져오기
                    maplePoint = this.mapleDutyCheckService.searchLastMaplePoint(server);

                    if (maplePoint == null) {
                        // 3) DB에도 없으면 에러 응답
                        event.reply("수집된 메이플 포인트 시세가 없습니다. 메이플 포인트 시세를 입력 해 다시 시도 해주세요.").queue();
                        break;
                    }
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
                        // 배율 유효성 검증 시작
                        if(distributionPercentOption == null){
                            event.reply("자율분배에 배율은 필수 값 입니다.")
                                    .setEphemeral(true)
                                    .queue();
                            break;
                        }

                        String peoplePercentOption = Objects.requireNonNull(distributionPercentOption).getAsString().replaceAll(" ","");

                        if(!peoplePercentOption.matches("^\\d+(?::\\d+)*$")){
                            event.reply("배율 형식은 x:y:z 입니다. 숫자만 입력 해주세요.")
                                    .setEphemeral(true)
                                    .queue();
                            break;
                        }

                        String[] parts = peoplePercentOption.split(":");
                        int partsSum = 0;
                        for(String part : parts){
                            partsSum += Integer.parseInt(part);
                        }

                        if(partsSum != 100){
                            event.reply("분배 비율의 총 합은 100이 되도록 입력 해주세요. 현재 합 : "+partsSum)
                                    .setEphemeral(true)
                                    .queue();
                            break;
                        }
                        //배율 유효성 검증 끝

                        //아이템 판매자(1번 수수료 제외)
                        double firstPeople = (double) Integer.parseInt(parts[0]) / 100;
                        // 아이템판매금액 /(아이템판매자 배율 + (1/수수료) * (1-아이템 판매지 비율 )) 이게 총 금액
                        BigDecimal sumAmt = BigDecimal.valueOf(itemAmt / (firstPeople+(1/fee) * (1-firstPeople)));

                        // [0] = 교환창에 올릴 메소(세전), [1] = 실 수령 메소(세후)
                        List<BigDecimal[]> shareList = new ArrayList<>();
                        // 아이템 판매자 금액을 제외하고 계산함.
                        for(int i = parts.length-1;i>=1;i--){
                            //현재 계산될 파티원의 배율
                            BigDecimal peoplePercent = BigDecimal
                                    .valueOf(Integer.parseInt(parts[i]))
                                    .divide(BigDecimal.valueOf(100), 4, java.math.RoundingMode.HALF_UP);

                            BigDecimal gross = sumAmt.multiply(peoplePercent);                        // 교환창에 올릴 금액
                            BigDecimal net   = gross.multiply(BigDecimal.valueOf(fee));               // 실 수령 금액

                            shareList.add(new BigDecimal[]{ gross, net });
                        }


                        BigDecimal firstGross = BigDecimal.valueOf(itemAmt * fee);
                        for (BigDecimal[] s : shareList) {
                            firstGross = firstGross.subtract(s[0]);
                        }

                        // 1번도 리스트에 추가 (앞에 넣고 싶으면 add(0, ...) 써도 됨)
                        shareList.add(new BigDecimal[]{ firstGross, firstGross });

                        StringBuilder sb = new StringBuilder();

                        sb.append("""
                        %s 분배
                        
                        입력 받은 분배 금액(수수료 제외) : %.1f억
                        분배 인원 : %d명
                        
                        아이템 판매자 (수수료제외)
                        """.formatted(distributionOption, afterFeeAmt, peopleCount));
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


        }



    }
}
