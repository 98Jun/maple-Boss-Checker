package com.let.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * packageName    : com.let.common
 * fileName       : CommonResponseDTO
 * author         : jun
 * date           : 25. 12. 29.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 29.        jun       최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CommonResponseDTO {
    @Schema(description = "결과값", example = "200")
    private int status;
    @Schema(description = "결과 메세지", example = "성공")
    private String msg;
}
