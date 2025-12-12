package com.let.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.let.domain
 * fileName       : MaplePointDutyCheckVO
 * author         : jun
 * date           : 25. 12. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 12.        jun       최초 생성
 */
@Getter
@Setter
@Builder
public class MaplePointDutyCheckVO {

    private Integer maplePoint;
    private String  server;

    public MaplePointDutyCheckVO(Integer maplePoint, String server) {
        this.maplePoint = maplePoint;
        this.server = server;
    }
}
