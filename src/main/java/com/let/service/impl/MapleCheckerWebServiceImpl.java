package com.let.service.impl;

import com.let.domain.ChannelVO;
import com.let.dto.ChannelDTO;
import com.let.service.MapleCheckerWebService;
import com.let.service.MapleUtilService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.let.service.impl
 * fileName       : MapleCheckerWebServiceImpl
 * author         : jun
 * date           : 25. 12. 24.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 24.        jun       최초 생성
 */
@Service
public class MapleCheckerWebServiceImpl implements MapleCheckerWebService {

    @Autowired
    private MapleUtilService mapleUtilService;

    @Override
    public ChannelDTO.channelResponse insertChannelId(ChannelDTO.channelRequest dto) {
        ChannelDTO.channelResponse result = new ChannelDTO.channelResponse();
        ChannelVO channelVO = dto.convertDTOtoVO(dto);

        // 이미 저장되어 있는 채널 아이디인지 유효성 검증
        ChannelVO searchChannel = this.mapleUtilService.searchChannelById(channelVO);

        if(searchChannel != null) {
            result.setMsg("이미 저장된 채널입니다.");
            result.setStatus(500);
            return result;
        }

        // 채널 아이디 저장
        ChannelVO insertChannel = this.mapleUtilService.insertChannel(channelVO);

        if(insertChannel == null || insertChannel.getChannelId().isEmpty()) {
            result.setMsg("채널 등록 중 오류가 발생했습니다. 관리자에게 문의 해 주세요.");
            result.setStatus(500);
            return result;
        }

        result.setMsg("OK");
        result.setStatus(200);
        result.setChannel(insertChannel);

        return result;
    }
}
