package com.let.service.impl;

import com.let.domain.MaplePartyScheduleVO;
import com.let.domain.MaplePartySearchVO;
import com.let.domain.MaplepartyMemberVO;
import com.let.service.MaplePartyScheduleService;
import com.let.service.impl.mapper.MaplePartyScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Clock;
import java.time.LocalTime;
import java.util.List;

/**
 * packageName    : com.let.service.impl
 * fileName       : MaplePartyScheduleServiceImpl
 * author         : jun
 * date           : 25. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 16.        jun       최초 생성
 */
@Service
public class MaplePartyScheduleServiceImpl implements MaplePartyScheduleService {

    @Autowired
    private MaplePartyScheduleMapper partyScheduleMapper;
    @Override
    public int insertMapleParytSchedule(MaplePartyScheduleVO maplePartyScheduleVO) {
        return this.partyScheduleMapper.insertMapleParytSchedule(maplePartyScheduleVO);
    }

    @Override
    public MaplePartyScheduleVO selectPartySchedule(MaplePartyScheduleVO maplePartyScheduleVO) {
        return this.partyScheduleMapper.selectPartySchedule(maplePartyScheduleVO);
    }

    @Override
    public int insertPartyMemberList(MaplepartyMemberVO memberVO) {
        return this.partyScheduleMapper.insertPartyMemberList(memberVO);
    }

    @Override
    public List<MaplePartySearchVO> searchPartySchedule() {
        return this.partyScheduleMapper.searchPartySchedule();
    }

    @Override
    public boolean isWithinNextTwoHours(Time target, Clock clock) {
        if (target == null) return false;

        LocalTime now = LocalTime.now(clock);
        LocalTime t = target.toLocalTime();

        int nowMin = now.getHour() * 60 + now.getMinute();
        int tMin   = t.getHour() * 60 + t.getMinute();

        // 0~1439 사이로 정규화 (자정 넘어가는 경우 포함)
        int diff = (tMin - nowMin + 24 * 60) % (24 * 60);

        return diff <= 120; // 0분
    }

    @Override
    public int updatePartyUseAt(MaplePartySearchVO schedule) {
        return this.partyScheduleMapper.updatePartyUseAt(schedule);
    }
}
