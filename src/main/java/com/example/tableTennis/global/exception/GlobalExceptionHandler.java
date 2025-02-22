package com.example.tableTennis.global.exception;

import com.example.tableTennis.global.response.ApiResponse;
import com.example.tableTennis.global.response.ResponseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ApiResponse<?> handleBusinessException(CustomException e) {
        return ApiResponse.create(e.getResponseType());
    }

    @ExceptionHandler(RuntimeException.class)
    protected ApiResponse<?> handleRuntimeException(RuntimeException e) {
        return ApiResponse.create(ResponseType.ERROR);
    }
}
