package com.let.service;

import com.let.domain.MapleParytScheduleVO;

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
    int insertMapleParytSchedule(MapleParytScheduleVO mapleParytScheduleVO);

    MapleParytScheduleVO selectPartySchedule(MapleParytScheduleVO mapleParytScheduleVO);
}
