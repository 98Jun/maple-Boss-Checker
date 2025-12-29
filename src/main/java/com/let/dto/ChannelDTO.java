package com.let.dto;

import com.let.common.CommonResponseDTO;
import com.let.domain.ChannelVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName    : com.let.dto
 * fileName       : ChannelDTO
 * author         : jun
 * date           : 25. 12. 24.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 24.        jun       최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChannelDTO {

    @Getter
    @Setter
    @ToString
    public static class channelRequest{
        @Schema(description = "디스코드 채널 아이디")
        private String  channelId;
        @Schema(description = "디스코드 채널명 및 메모")
        private String  channelMemo;

        public ChannelVO convertDTOtoVO(ChannelDTO.channelRequest channelDTO) {

            return ChannelVO.builder()
                    .channelId(channelDTO.getChannelId())
                    .channelMemo(channelDTO.getChannelMemo())
                    .build();
        }
    }
    @Getter
    @Setter
    @ToString
    public static class channelResponse extends CommonResponseDTO {
        private ChannelVO channel;
    }
}
