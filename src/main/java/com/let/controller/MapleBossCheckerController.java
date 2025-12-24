package com.let.controller;

import com.let.dto.ChannelDTO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.let.controller
 * fileName       : MapleBossCheckerController
 * author         : jun
 * date           : 25. 12. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 10.        jun       최초 생성
 */
@RestController("/test")
public class MapleBossCheckerController {

    @PostMapping
    public ResponseEntity<?> insertChannelId(@ParameterObject ChannelDTO dto){

        return ResponseEntity.ok(null);
    }
}
