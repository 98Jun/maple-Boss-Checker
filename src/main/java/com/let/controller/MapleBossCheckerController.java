package com.let.controller;

import com.let.dto.ChannelDTO;
import com.let.service.MapleCheckerWebService;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController("/channel")
public class MapleBossCheckerController {

    @Autowired
    private MapleCheckerWebService mapleCheckerWebService;

    @PostMapping("/register")
    @Operation(description = "채널 등록")
    public ResponseEntity<?> insertChannelId(@ParameterObject ChannelDTO dto){
        var result = mapleCheckerWebService.insertChannelId(dto);
        return ResponseEntity.ok(null);
    }
}
