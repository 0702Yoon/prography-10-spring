package com.example.tableTennis.domain.userRoom.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record EnterRoomRequestDto(
    @Schema(description = "방에 참가할 회원의 ID")
    Integer userId
) {

}
