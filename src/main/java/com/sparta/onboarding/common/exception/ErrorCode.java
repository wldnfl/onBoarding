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
    EMAIL_MISMATCH(HttpStatus.NOT_FOUND, "이메일이 일치하지 않습니다."),
    USER_NOT_UNIQUE(HttpStatus.BAD_REQUEST, "중복된 유저가 존재합니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "유효하지 않은 비밀번호 형식입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "현재 비밀번호가 틀렸습니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "유효하지 않은 인증코드입니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "이메일 인증이 완료되지 않았습니다."),
    NICKNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 사용 중인 닉네임입니다."),

    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "중복된 username 입니다."),
    INVALID_USERNAME_OR_PASSWORD(HttpStatus.BAD_REQUEST, "username 또는 비밀번호가 유효하지 않습니다."),
    INVALID_ADMIN_TOKEN(HttpStatus.UNAUTHORIZED, "관리자 암호가 틀려 등록이 불가능합니다."),
    LOGIN_ATTEMPT_FAILED(HttpStatus.BAD_REQUEST, "로그인 시도 중 오류가 발생했습니다."),

    // schedule
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 아이디에 맞는 일정을 찾을 수 없습니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    SCHEDULE_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 일정입니다."),

    // comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "댓글 혹은 일정의 아이디가 없습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "사용자가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
