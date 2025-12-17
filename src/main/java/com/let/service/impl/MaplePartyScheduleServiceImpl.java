package com.let.service.impl;

import com.let.domain.MaplePartyScheduleVO;
import com.let.domain.MaplepartyMemberVO;
import com.let.service.MaplePartyScheduleService;
import com.let.service.impl.mapper.MaplePartyScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<MaplepartyMemberVO> searchPartySchedule() {
        return this.partyScheduleMapper.searchPartySchedule();
    }
}
