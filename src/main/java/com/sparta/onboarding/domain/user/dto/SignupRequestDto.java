package com.sparta.onboarding.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequestDto {

    @NotBlank(message = "사용자 이름은 필수값 입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private final String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    @NotBlank(message = "비밀번호는 필수값 입니다.")
    private final String password;

    @NotBlank(message = "User ID는 필수값 입니다.")
    private final String userId;

    private final boolean admin;

    private final String adminToken;
}
