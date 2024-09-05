package com.sparta.onboarding.common.response;

import lombok.Getter;

@Getter
public class StatusCommonResponse {

    private final Integer httpStatusCode;
    private final String message;

    public StatusCommonResponse(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}