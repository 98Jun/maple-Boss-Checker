package com.let.scheduler;

import com.let.domain.MaplePartySearchVO;
import com.let.domain.MaplepartyMemberVO;
import com.let.service.MaplePartyScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : com.let.scheduler
 * fileName       : MaplePartyScheduler
 * author         : jun
 * date           : 25. 12. 17.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 17.        jun       최초 생성
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MaplePartyScheduler {

    private final JDA jda;

    @Autowired
    private final MaplePartyScheduleService maplePartyScheduleService;

    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul")
//    @Scheduled(cron = "0 */5 * * * *", zone = "Asia/Seoul") //테스트
    public void checkParty() {
        //일정이 있는지 조회
        List<MaplePartySearchVO> memberVOList = this.maplePartyScheduleService.searchPartySchedule();
        //
        for(MaplePartySearchVO schedule : memberVOList){

            // 두시간 전  체크
            boolean afterTimeCheck  = maplePartyScheduleService.isWithinNextTwoHours(schedule.getTime(), Clock.systemDefaultZone());
            if(!afterTimeCheck) continue;

            //호출 할 멤버가 없으면 실행하지 않는다
            List<String> partyUser = schedule.getMemberDiscordId();
            if(partyUser == null || partyUser.isEmpty() ){
                this.maplePartyScheduleService.updatePartyUseAt(schedule);
                continue;
            }

            // 3) 멘션 문자열 만들기: <@123><@456>...
            String mentions = partyUser.stream()
                    .map(id -> "<@" + id + ">")
                    .collect(Collectors.joining(" "));

            String message =
                    "⏰ **파티 일정 알림**\n" +
                            "제목: " + schedule.getTitle() + "\n" +
                            "시간: " + schedule.getDate() + " " + schedule.getTime() + "\n" +
                            "참여자: " + mentions;

            // 4) 보낼 채널 (채널ID를 DB에 저장해두거나, 고정 채널이면 config로)
            TextChannel channel = jda.getTextChannelById(schedule.getChannelId());
            if (channel == null) {
                log.warn("채널을 찾을 수 없음. channelId={}", schedule.getChannelId());
                continue;
            }

            // 5) 전송
            channel.sendMessage(message).queue(
                    ok -> this.maplePartyScheduleService.updatePartyUseAt(schedule), // 성공 시 알림 처리
                    err -> log.error("알림 전송 실패 scheduleId={}", schedule.getId(), err)
            );
        }

    }
}
