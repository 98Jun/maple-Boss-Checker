package com.let.service.impl;

import com.let.domain.MapleParytScheduleVO;
import com.let.service.MaplePartyScheduleService;
import com.let.service.impl.mapper.MaplePartyScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public MapleParytScheduleVO insertMapleParytSchedule(MapleParytScheduleVO mapleParytScheduleVO) {
        return this.partyScheduleMapper.insertMapleParytSchedule(mapleParytScheduleVO);
    }
}
