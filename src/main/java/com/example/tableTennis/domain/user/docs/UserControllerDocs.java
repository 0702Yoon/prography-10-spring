package com.example.tableTennis.domain.user.docs;

import com.example.tableTennis.domain.user.controller.dto.response.UserPageResponse;
import com.example.tableTennis.global.response.ApiResponse;
import com.example.tableTennis.global.response.ResponseType;
import com.example.tableTennis.global.response.example.error.ApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "USER API", description = "유저 관련 API")
public interface UserControllerDocs {

    @Operation(summary = "회원 전체 조회")
    @ApiErrorCodeExamples({ResponseType.FAIL, ResponseType.ERROR})
    ApiResponse<UserPageResponse> getAllUser(Integer page, Integer size);
}
