package com.let.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * packageName    : com.let.domain
 * fileName       : MaplePartySearchVO
 * author         : jun
 * date           : 25. 12. 17.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 17.        jun       최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MaplePartySearchVO {
    @Schema(example = "1",description = "일정 키값 아이디")
    private int id;
    @Schema(example = "일정일걸요",description = "일정명")
    private String title;
    @Schema(example = "131294820",description = "호출 채널 아이디")
    private String channelId;
    @Schema(example = "yyyy.MM.dd",description = "일정 설정 날짜")
    private Date date;
    @Schema(example = "HH:mm",description = "일정 설정 시간")
    private Time time;

    @Schema(example = "1239812031",description = "일정 참여 멤버 디스코드 아이디" )
    private List<String> memberDiscordId;

}
