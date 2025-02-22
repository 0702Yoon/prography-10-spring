package com.example.tableTennis.domain.common.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "초기화에 필요한 요청")
public record InitializeRequestDto(
    @Schema(description = "난수 시작 번호")
    int seed,
    @Schema(description = "회원의 수")
    int quantity
) {

}
