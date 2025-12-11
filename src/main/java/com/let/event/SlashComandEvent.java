package com.let.event;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.beans.factory.annotation.Value;
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
public class SlashComandEvent extends ListenerAdapter {

    @Value("${maple.api.key}")
    private String mapleApiKey;
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandDatas = new ArrayList<>();
        commandDatas.add(
                Commands.slash("캐릭터명", "해당 캐릭터의 정보를 조회합니다.")
                        .addOption(OptionType.STRING, "캐릭터명", "엽상", true)
        );
        commandDatas.add(
                Commands.slash("관세계산기", "아이템의 관세를 계산합니다. (억단위) 메포시세 미 입력 시 가장 최근에 검색된 값 사용")
                        .addOption(OptionType.INTEGER, "아이템금액", "100", true)
                        .addOption(OptionType.INTEGER, "메포시세", "2165", false)
        );

        event.getGuild().updateCommands().addCommands(commandDatas).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){

        if(!event.getChannel().getId().equals("1448173918283108469")) return;
        System.out.println(event.getId());
        System.out.println(event.getName());

        String eventName = event.getName();
        switch (eventName){
            case "관세계산기":
                //아이템 금액
                int itemPay = Objects.requireNonNull(event.getOption("아이템금액")).getAsInt();

                var mapleOption = event.getOption("메포시세");

                Integer maplePoint = null;
                if (mapleOption == null) {
                    event.reply("수집된 메이플 포인트 시세가 없습니다. 메이플 포인트 시세를 입력 해 다시시도 해주세요.").queue();
                    break;
                } else {
                    // 👉 메포시세를 직접 입력한 경우
                    maplePoint = mapleOption.getAsInt();
                }

                //바꿔야할 메소(억단위)
                int myPayMeso = (itemPay / 10);
                //충전해야할 메이플 포인트 금액
                int myPayPoint =  myPayMeso * maplePoint;

                //현 서버에 동일한 금액의 아이템 가격
                int myServerItemAmt = itemPay + myPayMeso;


                // 한 메세지에 세줄로 전달
                event.reply("""
                        입력 받은 아이템 금액 : %d억
                        충전 해야할 메이플 포인트 : %d
                        충전에 사용될 메소 (억단위) : %d억
                        내 서버에서 동일한 금액의 아이템 금액 : %d억
                        """.formatted(itemPay,myPayPoint, myPayMeso, myServerItemAmt)
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
