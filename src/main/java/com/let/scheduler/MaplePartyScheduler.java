package com.let.scheduler;

import com.let.domain.MaplepartyMemberVO;
import com.let.service.MaplePartyScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public void checkParty() {
        //일정이 있는지 조회
        List<MaplepartyMemberVO> memberVOList = this.maplePartyScheduleService.searchPartySchedule();
        //
        for(MaplepartyMemberVO member : memberVOList){

        }

    }
}
