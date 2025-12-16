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

    private static final String DATE_REGEX = "^(19|20)\\d{2}\\.(0[1-9]|1[0-2])\\.(0[1-9]|[12]\\d|3[01])$";
    private static final String TIME_REGEX = "^([01]\\d|2[0-3]):[0-5]\\d$";

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

    @Override
    public boolean checkDateTime(String date, String time) {
        boolean dateCheck = date != null && date.matches(DATE_REGEX);
        boolean timeCheck = time != null && time.matches(TIME_REGEX);
        return dateCheck && timeCheck;
    }
}
