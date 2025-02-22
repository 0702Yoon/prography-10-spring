package com.example.tableTennis.domain.common.docs;

import com.example.tableTennis.domain.common.controller.dto.request.InitializeRequestDto;
import com.example.tableTennis.global.response.ApiResponse;
import com.example.tableTennis.global.response.ResponseType;
import com.example.tableTennis.global.response.example.error.ApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Common API", description = "서버 관련 API")
public interface CommonControllerDocs {

    @Operation(summary = "헬스 체크", description = "애플리케이션의 상태를 확인합니다.")
    @ApiErrorCodeExamples({ResponseType.FAIL, ResponseType.ERROR})
    ApiResponse<?> healthCheck();

    @Operation(summary = "초기화", description = "데이터베이스의 모든 내용을 지우고, 초기화 합니다.")
    @ApiErrorCodeExamples({ResponseType.FAIL, ResponseType.ERROR})
    ApiResponse<?> initialize(InitializeRequestDto initializeRequestDto);
}
