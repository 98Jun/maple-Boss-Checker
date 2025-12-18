package com.let.service;

import com.let.domain.MaplePartyScheduleVO;
import com.let.domain.MaplePartySearchVO;
import com.let.domain.MaplepartyMemberVO;

import java.sql.Time;
import java.time.Clock;
import java.util.List;

/**
 * packageName    : com.let.service
 * fileName       : MaplePartyScheduleService
 * author         : jun
 * date           : 25. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 16.        jun       최초 생성
 */
public interface MaplePartyScheduleService {
    int insertMapleParytSchedule(MaplePartyScheduleVO maplePartyScheduleVO);

    MaplePartyScheduleVO selectPartySchedule(MaplePartyScheduleVO maplePartyScheduleVO);

    int insertPartyMemberList(MaplepartyMemberVO memberVO);

    List<MaplePartySearchVO> searchPartySchedule();

    boolean isWithinNextTwoHours(Time target, Clock clock);
}
