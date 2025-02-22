package com.example.tableTennis.domain.common.controller;

import com.example.tableTennis.domain.common.controller.dto.request.InitializeRequestDto;
import com.example.tableTennis.domain.common.docs.CommonControllerDocs;
import com.example.tableTennis.domain.common.service.CommonService;
import com.example.tableTennis.global.response.ApiResponse;
import com.example.tableTennis.global.response.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommonController implements CommonControllerDocs {

    private final CommonService commonService;

    @Override
    @GetMapping("/health")
    public ApiResponse<?> healthCheck() {
        return ApiResponse.create(ResponseType.SUCCESS);
    }

    @Override
    @PostMapping("/init")
    public ApiResponse<?> initialize(@RequestBody InitializeRequestDto initializeRequestDto) {
        commonService.initialize(initializeRequestDto);
        return ApiResponse.create(ResponseType.SUCCESS);
    }
}
