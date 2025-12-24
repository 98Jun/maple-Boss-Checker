package com.let.dto;

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
    private String  channelId;
    private String  channelMemo;

}
