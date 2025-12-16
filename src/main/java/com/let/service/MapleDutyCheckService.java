package com.let.service;

import com.let.domain.MaplePointDutyCheckVO;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

/**
 * packageName    : com.let.service
 * fileName       : MapleDutyCheckService
 * author         : jun
 * date           : 25. 12. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 12.        jun       최초 생성
 */

public interface MapleDutyCheckService {
    Integer searchLastMaplePoint(String server);

    Integer insertMaplePointHistory(MaplePointDutyCheckVO vo);

    Integer checkCommandMaplePoint(OptionMapping mapleOption, String server);
}
