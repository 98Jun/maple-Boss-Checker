package com.let.service;

import com.let.dto.ChannelDTO;
import org.json.JSONObject;

/**
 * packageName    : com.let.service
 * fileName       : MapleCheckerWebService
 * author         : jun
 * date           : 25. 12. 24.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 24.        jun       최초 생성
 */
public interface MapleCheckerWebService {
    ChannelDTO.channelResponse insertChannelId(ChannelDTO.channelRequest dto);
}
