package com.let.service.impl;

import com.let.service.MapleDistributionService;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * packageName    : com.let.service.impl
 * fileName       : MapleDistributionServiceImpl
 * author         : jun
 * date           : 25. 12. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 15.        jun       최초 생성
 */
@Service
public class MapleDistributionServiceImpl implements MapleDistributionService {

    @Override
    public JSONObject autonomousDistributionValidationCheck(OptionMapping distributionPercentOption) {
        JSONObject result = new JSONObject();
        String msg = null;
        boolean isOk = true;
        // 배율 유효성 검증 시작
        if(distributionPercentOption == null){
            msg = "자율분배에 배율은 필수 값 입니다.";
            isOk = false;
        }

        String peoplePercentOption = Objects.requireNonNull(distributionPercentOption).getAsString().replaceAll(" ","");


        if(!peoplePercentOption.matches("^\\d+(?::\\d+)*$")){
            msg = "배율 형식은 x:y:z 입니다. 숫자만 입력 해주세요.";
            isOk = false;
        }

        String[] parts = peoplePercentOption.split(":");
        int partsSum = 0;
        for(String part : parts){
            partsSum += Integer.parseInt(part);
        }

        if(partsSum != 100){
            msg = "분배 비율의 총 합은 100이 되도록 입력 해주세요. 현재 합 : "+partsSum;
            isOk = false;
        }
        result.put("msg",msg);
        result.put("isOk",isOk);
        result.put("parts",parts);

        return result;
    }

    @Override
    public List<BigDecimal[]> getShareList(String[] parts, long itemAmt, double fee) {
        // [0] = 교환창에 올릴 메소(세전), [1] = 실 수령 메소(세후)
        List<BigDecimal[]> shareList = new ArrayList<>();

        //아이템 판매자(1번 수수료 제외)
        double firstPeople = (double) Integer.parseInt(parts[0]) / 100;
        // 아이템판매금액 /(아이템판매자 배율 + (1/수수료) * (1-아이템 판매지 비율 )) 이게 총 금액
        BigDecimal sumAmt = BigDecimal.valueOf(itemAmt / (firstPeople+(1/fee) * (1-firstPeople)));


        // 아이템 판매자 금액을 제외하고 계산함.
        for(int i = parts.length-1;i>=1;i--){
            //현재 계산될 파티원의 배율
            BigDecimal peoplePercent = BigDecimal
                    .valueOf(Integer.parseInt(parts[i]))
                    .divide(BigDecimal.valueOf(100), 4, java.math.RoundingMode.HALF_UP);

            BigDecimal gross = sumAmt.multiply(peoplePercent);                        // 교환창에 올릴 금액
            BigDecimal net   = gross.multiply(BigDecimal.valueOf(0.95));               // 실 수령 금액

            shareList.add(new BigDecimal[]{ gross, net });
        }


        BigDecimal firstGross = BigDecimal.valueOf(itemAmt * fee);
        for (BigDecimal[] s : shareList) {
            firstGross = firstGross.subtract(s[0]);
        }

        // 1번도 리스트에 추가 (앞에 넣고 싶으면 add(0, ...) 써도 됨)
        shareList.add(new BigDecimal[]{ firstGross, firstGross });

        return shareList;
    }
}
