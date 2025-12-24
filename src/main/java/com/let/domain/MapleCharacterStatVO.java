package com.let.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * packageName    : com.let.domain
 * fileName       : CharacterStatVO
 * author         : jun
 * date           : 25. 12. 24.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 24.        jun       최초 생성
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MapleCharacterStatVO(
        @JsonProperty("date") String date,
        @JsonProperty("character_class") String characterClass,
        @JsonProperty("final_stat") List<StatEntry> finalStat,
        @JsonProperty("remain_ap") int remainAp
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record StatEntry(
            @JsonProperty("stat_name") String name,
            @JsonProperty("stat_value") String value
    ) {}
}
