package com.let.service;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * packageName    : com.let.service
 * fileName       : MapleDistributionService
 * author         : jun
 * date           : 25. 12. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 15.        jun       최초 생성
 */
public interface MapleDistributionService {
    JSONObject autonomousDistributionValidationCheck(OptionMapping distributionPercentOption);


    List<BigDecimal[]> getShareList(String[] parts, long itemAmt, double fee);
}
