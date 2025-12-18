package com.let.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * packageName    : com.let.domain
 * fileName       : MapleparytMemberVO
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
public class MaplePartyMemberVO {
    @Schema(example = "1",description = "일정 키값 아이디 (maple_boss_party 의 id)")
    private int partyId;
    @Schema(example = "123123 ...",description = "일정에 태그된 회원들")
    private List<String> members;

    public MaplePartyMemberVO(int partyId, List<String> members) {
        this.partyId = partyId;
        this.members = members;
    }
}
