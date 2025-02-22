package com.example.tableTennis.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "성공 응답 객체")
public class ApiResponse<T> {

    @Schema(description = "응답 코드", example = "200")
    private Integer code;

    @Schema(description = "응답 메시지", example = "API 요청이 성공했습니다.")
    private String message;

    @JsonInclude(Include.NON_NULL)
    private T result;

    public static ApiResponse<?> create(ResponseType responseType) {
        return create(responseType, null);
    }

    public static <T> ApiResponse<T> create(ResponseType responseType, T result) {
        return ApiResponse.<T>builder()
            .code(responseType.getHttpStatusCode())
            .message(responseType.getMessage())
            .result(result)
            .build();
    }
}
