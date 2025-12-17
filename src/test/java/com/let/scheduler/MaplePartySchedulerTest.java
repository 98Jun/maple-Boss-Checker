package com.let.scheduler;

import com.let.service.MaplePartyScheduleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * packageName    : com.let.scheduler
 * fileName       : MaplePartySchedulerTest
 * author         : jun
 * date           : 25. 12. 17.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 17.        jun       최초 생성
 */
@SpringBootTest
public class MaplePartySchedulerTest {
    @Autowired
    private MaplePartyScheduler maplePartyScheduler;
    @MockitoBean
    private MaplePartyScheduleService scheduleService;
    @Test
    void schedulePartyTest() {
        maplePartyScheduler.checkParty();

        // 스케줄러가 내부에서 서비스 메서드를 호출했는지만(어떤 메서드인지는 상관없이) 확인
        assertFalse(Mockito.mockingDetails(scheduleService).getInvocations().isEmpty());
    }
}
