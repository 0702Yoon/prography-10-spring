package com.example.tableTennis.domain.userRoom.controller;

import com.example.tableTennis.domain.userRoom.controller.dto.request.ChangeTeamRequestDto;
import com.example.tableTennis.domain.userRoom.controller.dto.request.EnterRoomRequestDto;
import com.example.tableTennis.domain.userRoom.controller.dto.request.ExitRoomRequestDto;
import com.example.tableTennis.domain.userRoom.docs.UserRoomControllerDocs;
import com.example.tableTennis.domain.userRoom.service.UserRoomService;
import com.example.tableTennis.global.response.ApiResponse;
import com.example.tableTennis.global.response.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRoomController implements UserRoomControllerDocs {

    private final UserRoomService userRoomService;

    @Override
    @PostMapping("/room/attention/{roomId}")
    public ApiResponse<?> enterRoom(
        @PathVariable(name = "roomId") String roomId,
        @RequestBody EnterRoomRequestDto enterRoomRequestDto
    ) {
        userRoomService.createUserRoom(roomId, enterRoomRequestDto);
        return ApiResponse.create(ResponseType.SUCCESS);
    }

    @Override
    @PostMapping("/room/out/{roomId}")
    public ApiResponse<?> exitRoom(
        @PathVariable(name = "roomId") String roomId,
        @RequestBody ExitRoomRequestDto exitRoomRequestDto
    ) {
        userRoomService.deleteRoomParticipant(roomId, exitRoomRequestDto);
        return ApiResponse.create(ResponseType.SUCCESS);
    }

    @Override
    @PutMapping("/team/{roomId}")
    public ApiResponse<?> changeTeam(
        @PathVariable(name = "roomId") String roomId,
        @RequestBody ChangeTeamRequestDto changeTeamRequestDto
    ) {
        userRoomService.updateTeam(roomId, changeTeamRequestDto);
        return ApiResponse.create(ResponseType.SUCCESS);
    }
}
