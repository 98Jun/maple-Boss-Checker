package com.let.event;

import com.let.domain.MaplePartyScheduleVO;
import com.let.domain.MaplePartyMemberVO;
import com.let.domain.MemberVO;
import com.let.service.MaplePartyScheduleService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * packageName    : com.let.event
 * fileName       : ScheduleSelectListenerEvnet
 * author         : jun
 * date           : 25. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 16.        jun       최초 생성
 */
@Component
@RequiredArgsConstructor
public class ScheduleSelectListenerEvnet extends ListenerAdapter {

    @Autowired
    private MaplePartyScheduleService maplePartyScheduleService;

    @Override
    public void onEntitySelectInteraction(EntitySelectInteractionEvent event){

        //키값으로 저장된 일정 조회
        String key = event.getComponentId();
        MaplePartyScheduleVO scheduleVO = this.maplePartyScheduleService.selectPartySchedule(new MaplePartyScheduleVO(Integer.parseInt(key)));

        //유효성 체크
        if(scheduleVO == null || scheduleVO.getId()<=0) {
            event.reply("조회된 보스 일정이 없습니다.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        // 선택된 유저들
        List<User> selectedUsers = event.getMentions().getUsers();

        if (selectedUsers.isEmpty()) {
            event.reply("선택된 유저가 없습니다. 다시 시도해주세요.").setEphemeral(true).queue();
            return;
        }

        //일정등록 이후 보스파티 등록
        List<MemberVO> members = selectedUsers.stream()
                .map(u -> {
                    MemberVO vo = new MemberVO();
                    vo.setMemberDiscordId(u.getId());
                    vo.setMemberName(u.getGlobalName());
                    return vo;
                })
                .toList();
        MaplePartyMemberVO memberVO = new MaplePartyMemberVO(scheduleVO.getId(), members);
        int insertMemberParty = this.maplePartyScheduleService.insertPartyMemberList(memberVO);
        if(insertMemberParty <= 0){
            event.reply("일정 유저 저장 중 오류가 발생했습니다..").setEphemeral(true).queue();
            return;
        }

        //문구 작성 시작
        StringBuilder sb = new StringBuilder();
        sb.append("📅 일정 등록 완료\n")
                .append("일정 : ").append(scheduleVO.getTitle()).append("\n")
                .append("날짜 : ").append(scheduleVO.getDate()).append("\n")
                .append("시간 : ").append(scheduleVO.getTime()).append("\n")
                .append("참여 멤버:\n");

        for (User user : selectedUsers) {
            sb.append("- ").append(user.getAsMention()).append("\n");

        }


        String[] ids = selectedUsers.stream()
                .map(User::getId)
                .toArray(String[]::new);

        event.reply(sb.toString())
                .mentionUsers(ids)   // 이 유저들 멘션 허용
                .queue();
    }
}
