package com.let.scheduler;

import com.let.domain.MaplePartySearchVO;
import com.let.service.MaplePartyScheduleService;
import net.dv8tion.jda.api.JDA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * 스케줄러 메서드 자체가 "호출 가능"한지 확인하는 최소 단위 테스트.
 * (Spring ApplicationContext를 띄우지 않아서, 토큰/키 같은 환경변수(@Value) 누락으로 테스트가 실패하지 않음)
 */
@ExtendWith(MockitoExtension.class)
class MaplePartySchedulerTest {

    @Mock
    private MaplePartyScheduleService scheduleService;

    // 스케줄러가 JDA를 의존하고 있는 경우가 많아서 기본으로 mock 처리
    @Mock
    private JDA jda;

    @InjectMocks
    private MaplePartyScheduler maplePartyScheduler;

    @Test
    void checkParty_runs_without_exception() {
        assertDoesNotThrow(() -> maplePartyScheduler.checkParty());
    }

    @Test
    void  test_checkParty_runs_with_exception() {
        List<MaplePartySearchVO>  t= this.scheduleService.searchPartySchedule();

        System.out.println(t.size());
    }
}
