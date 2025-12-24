package com.let.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.let.service.impl.CharacterItemMessageServiceImpl;

import java.util.List;

/**
 * packageName    : com.let.domain
 * fileName       : MapleCharacterItemVO
 * author         : jun
 * date           : 25. 12. 24.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 24.        jun       최초 생성
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MapleCharacterItemVO(
        @JsonProperty("item_equipment")
        List<ItemEquipmentDTO> itemEquipment
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ItemEquipmentDTO(
            @JsonProperty("item_equipment_part")
            String itemEquipmentPart,

            @JsonProperty("item_name")
            String itemName,

            @JsonProperty("starforce")
            String starforce,

            @JsonProperty("potential_option_grade")
            String potentialOptionGrade,

            @JsonProperty("additional_potential_option_grade")
            String additionalPotentialOptionGrade
    ) {
    }
}
