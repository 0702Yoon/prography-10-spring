package com.example.tableTennis.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseType {
    SUCCESS(200, "API 요청이 성공했습니다."),
    FAIL(201, "불가능한 요청입니다."),
    ERROR(500, "에러가 발생했습니다.");
    private final int httpStatusCode;
    private final String message;
}
