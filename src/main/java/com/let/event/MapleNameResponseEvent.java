package com.let.event;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * packageName    : com.let.event
 * fileName       : MapleNameResponseEvent
 * author         : jun
 * date           : 25. 12. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 10.        jun       최초 생성
 */
public class MapleNameResponseEvent extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        //응답을 한번만 하기 위함
        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;

        String id =event.getChannel().getId();
        if(!id.equals("1448173918283108469")) return;

        //
        String message = event.getMessage().getContentDisplay();
        if(message.equals("!명령어")){

        event.getChannel().sendMessage("""
                        제가가진 명령어는 
                        1. 관세 계산기(고도화 예정 내부 db 사용하여 메이플포인트 시세 수집)
                        2. 분배금 계산기 (개발예정)
                        3. 보스 일정 알리미(개발예정)
                        입니다.
                        """).queue();
        }

    }
}
