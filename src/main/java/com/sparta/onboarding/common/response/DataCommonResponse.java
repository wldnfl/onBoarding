package com.sparta.onboarding.common.response;

import lombok.Getter;

@Getter
public class DataCommonResponse<T> {

    private final Integer httpStatusCode;
    private final String message;
    private final T data;

    public DataCommonResponse(int httpStatusCode, String message, T data) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = data;
    }
}