package com.let.controller;

import com.let.dto.ChannelDTO;
import com.let.service.MapleCheckerWebService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
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
@Tag(name = "봇 채널관련 컨트롤러")
public class MapleBossCheckerChannelController {

    @Autowired
    private MapleCheckerWebService mapleCheckerWebService;

    @PostMapping("/register")
    @Operation(description = "채널 등록")
    public ResponseEntity<ChannelDTO.channelResponse> insertChannelId(@ParameterObject ChannelDTO.channelRequest dto){
        var result = mapleCheckerWebService.insertChannelId(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    @Operation(description = "채널id 로 내부 키값 조회")
    public ResponseEntity<?> searchChannelId(@Parameter String channelNumber){
        var result = mapleCheckerWebService.searchChannelId(channelNumber);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete")
    @Operation(description = "채널 리스트 제외")
    public ResponseEntity<?> deleteChannel(@Parameter int id){
        var result = mapleCheckerWebService.deleteChannel(id);
        return ResponseEntity.ok(result);
    }
}
