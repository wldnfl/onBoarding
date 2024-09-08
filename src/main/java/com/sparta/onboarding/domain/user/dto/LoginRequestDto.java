package com.sparta.onboarding.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDto {

    @NotBlank(message = "사용자 이름은 필수값 입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수값 입니다.")
    private String password;
}
