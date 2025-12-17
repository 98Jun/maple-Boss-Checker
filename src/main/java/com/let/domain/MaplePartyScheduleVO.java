package com.let.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;
import java.util.Date;

/**
 * packageName    : com.let.domain
 * fileName       : MapleParytScheduleVO
 * author         : jun
 * date           : 25. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 16.        jun       최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MaplePartyScheduleVO {
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
    @Schema(example = "Y",description = "알림호출여부 Y/N " )
    private String useAt;

    public MaplePartyScheduleVO(Date date, Time time, String title, String channelId) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.channelId = channelId;
    }
    public MaplePartyScheduleVO(int id){
        this.id = id;
    }

}

