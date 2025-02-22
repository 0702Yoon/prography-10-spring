package com.example.tableTennis.domain.userRoom.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ExitRoomRequestDto(
    @Schema(description = "방에서 나갈 회원의 ID")
    int userId
) {

}
