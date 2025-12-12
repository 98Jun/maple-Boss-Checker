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
                Commands.slash("캐릭터명", "해당 캐릭터의 정보를 조회합니다.")
                        .addOption(OptionType.STRING, "캐릭터명", "엽상", true)
        );
        commandDatas.add(
                Commands.slash("관세계산기", "아이템의 관세를 계산합니다. (억단위) 메포시세 미 입력 시 가장 최근에 검색된 값 사용")
                        .addOptions(
                                new OptionData(OptionType.INTEGER, "아이템금액", "100", true),
                                serverOption,
                                new OptionData(OptionType.INTEGER, "메포시세", "2165", false)
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
                        입력 받은 (미 입력 시 최신 메포시세) 메포 시세 : %d원
                        입력 받은 아이템 금액 : %d억
                        충전 해야할 메이플 포인트 : %d
                        충전에 사용될 메소 (억단위) : %d억
                        내 서버에서 동일한 금액의 아이템 금액 : %d억
                        """.formatted(maplePoint,itemPay,myPayPoint, myPayMeso, myServerItemAmt)
                                ).queue();
                break;
            case "분배금 계산기" :

            case "캐릭터명":
            String message = Objects.requireNonNull(event.getOption("캐릭터명")).getAsString();

            //캐릭터 존재 여부 파악을 위한 캐릭터 식별자 조회
//            WebClient webClient= WebClient.builder().build();
//            webClient.get()
//                            .uri("/maplestory/v1/id")
//                                    .
                break;
        }



    }
}
