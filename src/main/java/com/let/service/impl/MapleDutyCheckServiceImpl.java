package com.let.service.impl;

import com.let.domain.MaplePointDutyCheckVO;
import com.let.service.MapleDutyCheckService;
import com.let.service.impl.mapper.MapleDutyCheckMapper;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
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

    @Override
    public Integer checkCommandMaplePoint(OptionMapping mapleOption, String server) {
        Integer maplePoint = null ;

            if (mapleOption != null) {
                // 1) 사용자가 메포시세를 직접 입력한 경우 그 값 사용
                maplePoint = mapleOption.getAsInt();
                this.insertMaplePointHistory(new MaplePointDutyCheckVO(maplePoint,server));
            } else {
                // 2) 입력 안 했으면 DB에서 가져오기
                maplePoint = this.searchLastMaplePoint(server);

            }

        return maplePoint;
    }
}
