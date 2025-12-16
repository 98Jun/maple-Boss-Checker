package com.let.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
public class MapleParytScheduleVO {
    private int id;
    private Date date;
    private Time time;

    public MapleParytScheduleVO(Date date, Time time) {
        this.date = date;
        this.time = time;
    }
}
