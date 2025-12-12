package com.let.service.impl;

import com.let.domain.MaplePointDutyCheckVO;
import com.let.service.MapleDutyCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.let.service.impl
 * fileName       : MapleDutyCheckServiceImpl
 * author         : jun
 * date           : 25. 12. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 12.        jun       최초 생성
 */
@Service
public class MapleDutyCheckServiceImpl implements MapleDutyCheckService {

    @Autowired
    private MapleDutyCheckMapper dutyCheckMapper;

    @Override
    public Integer searchLastMaplePoint(String server) {
        return dutyCheckMapper.searchLastMaplePoint(server);
    }

    @Override
    public Integer insertMaplePointHistory(MaplePointDutyCheckVO vo) {
        return dutyCheckMapper.insertMaplePointHistory(vo);
    }
}
