package com.example.tableTennis.global.exception;

import com.example.tableTennis.global.response.ResponseType;
import lombok.Getter;


@Getter
public class CustomException extends RuntimeException {

    private final ResponseType responseType;

    public CustomException(ResponseType responseType) {
        super(responseType.getMessage());
        this.responseType = responseType;
    }
}
