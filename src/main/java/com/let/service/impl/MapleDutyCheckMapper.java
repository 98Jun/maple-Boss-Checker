package com.let.service.impl;

import com.let.domain.MaplePointDutyCheckVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName    : com.let.service.impl
 * fileName       : MapleDutyCheckMapper
 * author         : jun
 * date           : 25. 12. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 12.        jun       최초 생성
 */
@Mapper
public interface MapleDutyCheckMapper {
    Integer searchLastMaplePoint(@Param("server") String server);

    Integer insertMaplePointHistory(MaplePointDutyCheckVO vo);
}
