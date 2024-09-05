package com.sparta.onboarding.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 기본 예외 처리기
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> defaultException(HttpServletRequest request, Exception e) {
        log.error("defaultException: {}, URI: {}", e.getMessage(), request.getRequestURI(), e);  // Stack trace 추가

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .msg(ErrorCode.FAIL.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 커스텀 예외 처리기
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(HttpServletRequest request, CustomException e) {
        log.error("CustomException: {}, URI: {}", e.getErrorCode().getMessage(), request.getRequestURI(), e);

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .msg(e.getErrorCode().getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(exceptionResponse, e.getErrorCode().getStatus());
    }

    // 유효성 검증 실패 처리기
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> processValidationError(HttpServletRequest request, MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder builder = new StringBuilder();
        String msg = ErrorCode.FAIL.getMessage();

        // 첫 번째 필드 오류 정보 가져오기
        if (bindingResult.hasFieldErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            String fieldName = fieldError.getField();

            builder.append("[");
            builder.append(fieldName);
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" / 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");

            msg = builder.toString();  // 상세 오류 메시지
        }

        log.error("Validation Error: {}, URI: {}", msg, request.getRequestURI(), exception);  // Validation 오류 로그

        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .msg(msg)
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
