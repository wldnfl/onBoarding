package com.sparta.onboarding.domain.user;

import com.sparta.onboarding.common.exception.CustomException;
import com.sparta.onboarding.common.response.StatusCommonResponse;
import com.sparta.onboarding.domain.user.dto.LoginRequestDto;
import com.sparta.onboarding.domain.user.dto.SignupRequestDto;
import com.sparta.onboarding.domain.user.entity.User;
import com.sparta.onboarding.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j(topic = "UserController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "User SignUp", description = "회원 가입 기능입니다.")
    @PostMapping("/register")
    public ResponseEntity<StatusCommonResponse> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            for (FieldError fieldError : fieldErrors) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            StatusCommonResponse response = new StatusCommonResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "회원가입 정보가 잘못되었습니다."
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        userService.registerUser(requestDto);

        StatusCommonResponse response = new StatusCommonResponse(
                HttpStatus.CREATED.value(),
                "회원가입 성공"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "User Login", description = "로그인 기능입니다.")
    @PostMapping("/login")
    public ResponseEntity<StatusCommonResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            User user = userService.login(loginRequestDto);
            String token = jwtUtil.createToken(user.getUsername(), user.getRole());

            HttpHeaders headers = new HttpHeaders();
            headers.add(JwtUtil.AUTHORIZATION_HEADER, JwtUtil.BEARER_PREFIX + token);

            StatusCommonResponse response = new StatusCommonResponse(
                    HttpStatus.OK.value(),
                    "로그인 성공!"
            );
            return ResponseEntity.ok().headers(headers).body(response);
        } catch (CustomException e) {
            StatusCommonResponse response = new StatusCommonResponse(
                    e.getErrorCode().getStatus().value(), // Use getStatus() to get HttpStatus
                    e.getErrorCode().getMessage()
            );
            return ResponseEntity.status(e.getErrorCode().getStatus()).body(response);
        }
    }
}
