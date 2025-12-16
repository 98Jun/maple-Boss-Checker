package com.let.service;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.LinkedHashMap;
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
}
