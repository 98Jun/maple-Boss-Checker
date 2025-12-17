package com.let.service.impl;

import com.let.domain.MapleParytScheduleVO;
import com.let.domain.MapleparytMemberVO;
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
    public int insertMapleParytSchedule(MapleParytScheduleVO mapleParytScheduleVO) {
        return this.partyScheduleMapper.insertMapleParytSchedule(mapleParytScheduleVO);
    }

    @Override
    public MapleParytScheduleVO selectPartySchedule(MapleParytScheduleVO mapleParytScheduleVO) {
        return this.partyScheduleMapper.selectPartySchedule(mapleParytScheduleVO);
    }

    @Override
    public int insertPartyMemberList(MapleparytMemberVO memberVO) {
        return this.partyScheduleMapper.insertPartyMemberList(memberVO);
    }
}
