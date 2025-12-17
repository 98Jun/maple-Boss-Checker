package com.let.domain;

import lombok.Builder;
import lombok.Getter;
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
public class MapleParytScheduleVO {
    private int id;
    private String title;
    private String channelId;
    private Date date;
    private Time time;
    private String useAt;

    public MapleParytScheduleVO(Date date, Time time,String title,String channelId) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.channelId = channelId;
    }
    public MapleParytScheduleVO(int id){
        this.id = id;
    }
    public MapleParytScheduleVO(){

    }
}
