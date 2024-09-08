package com.sparta.onboarding.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 공통 오류 코드
    FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "실패했습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰을 찾을 수 없습니다."),

    // user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 유저를 찾을 수 없습니다."),
    USER_NOT_UNIQUE(HttpStatus.BAD_REQUEST, "중복된 유저가 존재합니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "유효하지 않은 비밀번호 형식입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "현재 비밀번호가 틀렸습니다."),
    NICKNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 사용 중인 닉네임입니다."),

    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "중복된 username 입니다."),
    INVALID_USERNAME(HttpStatus.BAD_REQUEST, "유효하지 않은 username 입니다."),
    INVALID_ADMIN_TOKEN(HttpStatus.UNAUTHORIZED, "관리자 암호가 틀려 등록이 불가능합니다."),
    LOGIN_ATTEMPT_FAILED(HttpStatus.BAD_REQUEST, "로그인 시도 중 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;
}
