package com.let.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.let.domain.MapleCharacterStatVO;

/**
 * packageName    : com.let.service
 * fileName       : CharacterItemMessageService
 * author         : jun
 * date           : 25. 12. 23.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 23.        jun       최초 생성
 */
public interface CharacterItemMessageService {
    String buildItemEquipmentMessage(JsonNode item);

    String buildStatsEquipmentMessage(MapleCharacterStatVO statVO);
}
