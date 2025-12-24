package com.let.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.let.domain.MapleCharacterItemVO;
import com.let.domain.MapleCharacterStatVO;
import com.let.service.CharacterItemMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * packageName    : com.let.service.impl
 * fileName       : CharacterItemMessageServiceImpl
 * author         : jun
 * date           : 25. 12. 23.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 23.        jun       최초 생성
 */
@Service
public class CharacterItemMessageServiceImpl implements CharacterItemMessageService {
        @Autowired
        private  ObjectMapper objectMapper;


        /**
         * Nexon 아이템-장비 응답(JsonNode)을 VO로 파싱해서, 디스코드에 뿌릴 메시지 문자열로 만든다.
         */
        public String buildItemEquipmentMessage(JsonNode itemJson) {
            if (itemJson == null || itemJson.isNull()) {
                return "아이템 정보: (없음)";
            }

            try {
                MapleCharacterItemVO dto = objectMapper.treeToValue(itemJson, MapleCharacterItemVO.class);

                List<MapleCharacterItemVO.ItemEquipmentDTO> list = dto.itemEquipment();
                if (list == null || list.isEmpty()) {
                    return "아이템 정보: (장착 장비 없음)";
                }

                // 보기 좋은 파트 우선순위(원하는 파트 있으면 여기에 추가)
                List<String> preferredParts = List.of(
                        "무기", "보조무기", "엠블렘",
                        "모자", "상의", "하의", "장갑", "망토", "신발",
                        "벨트", "어깨장식", "얼굴장식", "눈장식",
                        "귀고리", "반지1", "반지2", "반지3", "반지4",
                        "펜던트", "펜던트2"
                );

                // 파트명 기준으로 정렬(선호 파트 먼저, 그 외는 뒤)
                list = new ArrayList<>(list);
                list.sort(Comparator.comparingInt(it -> {
                    String part = safe(it.itemEquipmentPart());
                    int idx = preferredParts.indexOf(part);
                    return idx >= 0 ? idx : 999;
                }));

                StringBuilder sb = new StringBuilder();
                sb.append("🧰 장착 아이템 요약\n");

                for (MapleCharacterItemVO.ItemEquipmentDTO it : list) {
                    String part = safe(it.itemEquipmentPart());
                    String name = safe(it.itemName());
                    if (name.isBlank()) continue;

                    String star = safe(it.starforce());
                    String potGrade = safe(it.potentialOptionGrade());
                    String addPotGrade = safe(it.additionalPotentialOptionGrade());

                    // 한 줄 요약
                    sb.append("- ").append(part).append(": ").append(name);

                    if (!star.isBlank() && !star.equals("0")) sb.append(" (스타포스 ").append(star).append(")");
                    if (!potGrade.isBlank()) sb.append(" / 잠재: ").append(potGrade);
                    if (!addPotGrade.isBlank()) sb.append(" / 에잠: ").append(addPotGrade);
                    sb.append("\n");

                }

                return sb.toString().trim();

            } catch (Exception e) {
                // 파싱 실패 시: JsonNode로 최소 요약만
                int count = itemJson.path("item_equipment").isArray() ? itemJson.path("item_equipment").size() : 0;
                return "🧰 장착 아이템 요약\n- (파싱 실패) item_equipment 개수: " + count;
            }
        }

    @Override
    public String buildStatsEquipmentMessage(MapleCharacterStatVO statVO) {

        List<String> statsList = List.of(
                "데미지", "보스 몬스터 데미지", "최종 데미지",
                "방어율 무시","크리티컬 확률","크리티컬 데미지",
                "아케인포스","어센틱포스","STR","DEX","INT",
                "LUK","HP","아이템드롭률","버프 지속시간","재사용 대기시감 감소(%)",
                "전투력"
        );

        StringBuilder sb = new StringBuilder();
        sb.append("\n⚔ 캐릭터 스펙\n");
        if (statVO == null || statVO.finalStat() == null || statVO.finalStat().isEmpty()) {
            return sb.append("- (스탯 정보 없음)").toString().trim();
        }

        for(MapleCharacterStatVO.StatEntry stat : statVO.finalStat()){
            String statName = stat.name();
            for(String stats: statsList){

                if(!stats.equals(statName)){
                    sb.append(stat.name()).append(" :").append(stat.value()).append("\n");
                    break;
                }

            }
        }

        return sb.toString().trim();
    }

    private static String safe(String v) {
            return v == null ? "" : v;
        }
    }

