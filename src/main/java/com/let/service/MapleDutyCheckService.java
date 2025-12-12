package com.let.service;

import com.let.domain.MaplePointDutyCheckVO;

/**
 * packageName    : com.let.service
 * fileName       : MapleDutyCheckService
 * author         : jun
 * date           : 25. 12. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 12.        jun       최초 생성
 */

public interface MapleDutyCheckService {
    Integer searchLastMaplePoint(String server);

    Integer insertMaplePointHistory(MaplePointDutyCheckVO vo);
}
