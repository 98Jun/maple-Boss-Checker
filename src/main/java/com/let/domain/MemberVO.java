package com.let.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName    : com.let.domain
 * fileName       : MemberVO
 * author         : jun
 * date           : 25. 12. 19.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 19.        jun       최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberVO {
    private String memberDiscordId;
    private String memberName;
}
