package com.let.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
@Component
@RequiredArgsConstructor
public class MaplePartyScheduler {

    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul")
    public void checkParty() {

    }
}
