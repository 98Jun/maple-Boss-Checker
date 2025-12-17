package com.let.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.dv8tion.jda.api.entities.User;

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
public class MapleparytMemberVO {
    private int partyId;
    private List<User> members;

    public  MapleparytMemberVO(int partyId, List<User> members) {
        this.partyId = partyId;
        this.members = members;
    }
    public  MapleparytMemberVO() {}
}
