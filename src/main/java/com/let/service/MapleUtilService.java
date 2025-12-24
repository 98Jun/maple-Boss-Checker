package com.let.service;

import com.let.domain.ChannelVO;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.Time;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.let.service
 * fileName       : MapleUtilService
 * author         : jun
 * date           : 25. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 16.        jun       최초 생성
 */
public interface MapleUtilService {
    public OptionData setOptionData(OptionType optionType,String name, String description, boolean required , LinkedHashMap<String,String> option);
    public boolean checkDateTime(String date, String time);

    List<ChannelVO> getChannelList();
}
