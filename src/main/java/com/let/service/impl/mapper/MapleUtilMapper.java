package com.let.service.impl.mapper;

import com.let.domain.ChannelVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : com.let.service.impl.mapper
 * fileName       : MapleUtilMapper
 * author         : jun
 * date           : 25. 12. 24.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 24.        jun       최초 생성
 */
@Mapper
public interface MapleUtilMapper {
    List<ChannelVO> getChannelList();
}
