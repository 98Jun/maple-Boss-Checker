package com.let.domain;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "2275",description = "메이플 포인트 시세(억 단위)")
    private Integer maplePoint;
    @Schema(example = "BERRA",description = "서버명  베라,루나,스카니아 ..")
    private String  server;

    public MaplePointDutyCheckVO(Integer maplePoint, String server) {
        this.maplePoint = maplePoint;
        this.server = server;
    }
}
