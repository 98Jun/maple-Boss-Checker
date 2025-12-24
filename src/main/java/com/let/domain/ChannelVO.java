package com.let.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName    : com.let.domain
 * fileName       : ChannelVO
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
public class ChannelVO {
    @Schema(description = "키값",example = "1")
    private int id;
    @Schema(description = "디스코드 채널 아이디",example = "123123123")
    private String channelId;
    @Schema(description = "메모( 채널명 같은 )",example = "고담")
    private String channelMemo;
    @Schema(description = "사용여부",example = "Y")
    private String useAt;
}
