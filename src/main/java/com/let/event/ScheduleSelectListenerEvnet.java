package com.let.event;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
public class ScheduleSelectListenerEvnet extends ListenerAdapter {

    @Override
    public void onEntitySelectInteraction(EntitySelectInteractionEvent event){
        if (!event.getComponentId().equals("schedule:user-select")) {
            return;
        }

        // 선택된 유저들
        List<User> selectedUsers = event.getMentions().getUsers();

        if (selectedUsers.isEmpty()) {
            event.reply("선택된 유저가 없습니다. 다시 시도해주세요.").setEphemeral(true).queue();
            return;
        }

        StringBuilder sb = new StringBuilder();

        for(User user : selectedUsers){

        }
    }
}
