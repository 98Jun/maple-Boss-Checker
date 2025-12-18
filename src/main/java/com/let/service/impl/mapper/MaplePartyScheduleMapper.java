package com.let.service.impl.mapper;

import com.let.domain.MaplePartyScheduleVO;
import com.let.domain.MaplePartySearchVO;
import com.let.domain.MaplepartyMemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : com.let.service.impl
 * fileName       : MaplePartyScheduleMapper
 * author         : jun
 * date           : 25. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 16.        jun       최초 생성
 */
@Mapper
public interface MaplePartyScheduleMapper {
    int insertMapleParytSchedule(MaplePartyScheduleVO maplePartyScheduleVO);

    MaplePartyScheduleVO selectPartySchedule(MaplePartyScheduleVO maplePartyScheduleVO);

    int insertPartyMemberList(MaplepartyMemberVO memberVO);

    List<MaplePartySearchVO> searchPartySchedule();
}
