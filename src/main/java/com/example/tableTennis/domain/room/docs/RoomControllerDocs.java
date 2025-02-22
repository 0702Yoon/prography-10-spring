package com.example.tableTennis.domain.room.docs;

import com.example.tableTennis.domain.room.controller.dto.request.CreateRoomRequestDto;
import com.example.tableTennis.domain.room.controller.dto.request.StartRoomRequestDto;
import com.example.tableTennis.domain.room.controller.dto.response.RoomDetailResponseDto;
import com.example.tableTennis.domain.room.controller.dto.response.RoomPageResponseDto;
import com.example.tableTennis.global.response.ApiResponse;
import com.example.tableTennis.global.response.ResponseType;
import com.example.tableTennis.global.response.example.error.ApiErrorCodeExample;
import com.example.tableTennis.global.response.example.error.ApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Room API", description = "방 관련 API")
public interface RoomControllerDocs {

    @ApiErrorCodeExamples({ResponseType.FAIL, ResponseType.ERROR})
    @Operation(summary = "방 만들기", description = "유저가 방을 만들면서 참여합니다.")
    ApiResponse<?> createRoom(CreateRoomRequestDto createRoomRequestDto);

    @ApiErrorCodeExamples({ResponseType.FAIL, ResponseType.ERROR})
    @Operation(summary = "방 전체 조회", description = "만들어진 방을 전부 조회합니다.")
    ApiResponse<RoomPageResponseDto> getAllRoom(Integer page, Integer size);

    @ApiErrorCodeExamples({ResponseType.FAIL, ResponseType.ERROR})
    @Operation(summary = "방 상세 정보 조회", description = "특정 방의 상세 정보를 조회합니다.")
    ApiResponse<RoomDetailResponseDto> getRoomDetail(String roomId);

    @ApiErrorCodeExamples({ResponseType.FAIL, ResponseType.ERROR})
    @Operation(summary = "게임방을 시작합니다.", description = "특정 방을 시작합니다. 단 방에 유저수가 조건에 맞게 존재 해야 시작할 수 있습니다.")
    ApiResponse<?> startRoom(String roomId, StartRoomRequestDto startRoomRequestDto);
}
