package com.let.event;

import com.let.domain.MaplePartySearchVO;
import com.let.domain.MemberVO;
import com.let.service.MaplePartyScheduleService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
@Component
@RequiredArgsConstructor
public class MapleNameResponseEvent extends ListenerAdapter {

    @Autowired
    private MaplePartyScheduleService maplePartyScheduleService;
    public void onMessageReceived(MessageReceivedEvent event) {
        //응답을 한번만 하기 위함
        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;

        String id =event.getChannel().getId();
        //고담 , 봇테 채널
        if(!id.equals("1450034042517852182") && !id.equals("1448173918283108469")) return;

        //
        String message = event.getMessage().getContentDisplay();
        switch (message){
            case "!명령어" :
                event.getChannel().sendMessage("""
                                제가가진 명령어는 
                                1. 관세계산기 
                                    사용법 /관세계산기
                                2. 분배금계산기
                                    사용법 /분배금계산기
                                3. 보스 일정 알리미
                                    사용법 /일정등록 
                                4. 등록된 일정 확인 (당일 2시간 전 멘션으로 호출)
                                    사용법 !일정확인
                                5. 간단한 캐릭터 정보조회 (개발 중 NEXON API 사용)
                                    사용법 /캐릭터조회
                                입니다.
                                """).queue();
                break;
            case "!일정확인" :
                //일정이 있는지 조회
                List<MaplePartySearchVO> memberVOList = this.maplePartyScheduleService.searchPartySchedule();

                StringBuilder sb = new StringBuilder();
                sb.append("현재까지 기록된 일정은 다음과 같습니다.\n\n");
                for(MaplePartySearchVO memberVO : memberVOList){
                    sb.append("📅\n 일정 :").append(memberVO.getTitle()).append("\n")
                            .append("날짜 : ").append(memberVO.getDate()).append("\n")
                            .append("시간 : ").append(memberVO.getTime()).append("\n")
                            .append("참여 멤버:\n");
                    for (MemberVO member : memberVO.getMember()){
                        sb.append("- ").append(member.getMemberName()).append("\n");

                    }
                }
                event.getChannel().sendMessage(sb).queue();
                break;
        }

    }
}
