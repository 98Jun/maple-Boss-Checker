package com.let.service.impl;

import com.let.common.CommonResponseDTO;
import com.let.domain.ChannelVO;
import com.let.dto.ChannelDTO;
import com.let.service.MapleCheckerWebService;
import com.let.service.MapleUtilService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        try {
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

        }catch (Exception e){
            result.setStatus(501);
            result.setMsg("채널 등록에 실패 했습니다. 관라자에게 문의 해 주세요. error : "+e.getMessage());
        }

        return result;
    }

    @Override
    public ChannelDTO.channelResponse searchChannelId(String channelNumber) {

        ChannelDTO.channelResponse result = new ChannelDTO.channelResponse();

        try{
            ChannelVO searchChannel = this.mapleUtilService.searchChannelById(ChannelVO.builder()
                    .channelId(channelNumber)
                    .build());

            if(searchChannel == null) {
                result.setStatus(500);
                result.setMsg("등록되지 않은 채널 아이디입니다.");
                return result;
            }

            result.setMsg("OK");
            result.setStatus(200);
            result.setChannel(searchChannel);

        }catch (Exception e){
            result.setStatus(500);
            result.setMsg("채널 조회 중 오류가 발생했습니다. 관리자에게 문의 해 주세요. error : "+e.getMessage());
        }

        return result;
    }

    @Override
    public CommonResponseDTO deleteChannel(int id) {
        CommonResponseDTO result = new CommonResponseDTO();

        try {
            //채널 삭제
            Integer deleteChannel = this.mapleUtilService.deleteChannel(id);

            if(deleteChannel == null || deleteChannel == 0) {
                result.setStatus(500);
                result.setMsg("채널 리스트에서 제외가 되지 않았습니다.");
                return result;
            }
            result.setMsg("OK");
            result.setStatus(200);

        }catch (Exception e){
            result.setStatus(500);
            result.setMsg("채널 리스트에서 제외에 실패했습니다. 관리자에게 문의 해 주세요. error : "+e.getMessage());
        }

        return result;
    }

    @Override
    public ChannelDTO.channelListResponse getChannelList() {
        ChannelDTO.channelListResponse result = new ChannelDTO.channelListResponse();
        try{
            List<ChannelVO> ChannelList = this.mapleUtilService.getChannelList();

            if(ChannelList == null || ChannelList.isEmpty()) {
                result.setStatus(500);
                result.setMsg("채널 리스트가 비어있습니다. ");
                return result;
            }
            result.setMsg("OK");
            result.setStatus(200);
            result.setChannels(ChannelList);

        }catch (Exception e){
            result.setStatus(500);
            result.setMsg("채널 리스트 조회가 실패 했습니다. error : " +e.getMessage());
        }
        return result;
    }
}
