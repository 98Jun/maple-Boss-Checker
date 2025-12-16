package com.let.service.impl;

import com.let.service.MapleUtilService;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * packageName    : com.let.service.impl
 * fileName       : MapleUtilService
 * author         : jun
 * date           : 25. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 16.        jun       최초 생성
 */
@Service
public class MapleUtilServiceImpl implements MapleUtilService {

    @Override
    public OptionData setOptionData(OptionType optionType,String name, String description, boolean required , LinkedHashMap<String,String> option) {
        OptionData optionData = new OptionData(
                optionType,
                name,
                description,
                required // required
        );

        if(option!=null){
            option.forEach(optionData::addChoice);
        }
        return optionData;
    }
}
