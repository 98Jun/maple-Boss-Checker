package com.let.service.impl.mapper;

import com.let.domain.MapleParytScheduleVO;
import org.apache.ibatis.annotations.Mapper;

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
    MapleParytScheduleVO insertMapleParytSchedule(MapleParytScheduleVO mapleParytScheduleVO);
}
