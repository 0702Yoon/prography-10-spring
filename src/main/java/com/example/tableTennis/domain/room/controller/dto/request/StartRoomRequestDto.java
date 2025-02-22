package com.example.tableTennis.domain.room.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record StartRoomRequestDto(
    @Schema(description = "게임을 시작할 방을 만든 호스트 ID ")
    Integer userId
) {

}
