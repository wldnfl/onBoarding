package com.sparta.onboarding.domain.user;

import com.sparta.onboarding.common.exception.CustomException;
import com.sparta.onboarding.common.exception.ErrorCode;
import com.sparta.onboarding.domain.user.dto.LoginRequestDto;
import com.sparta.onboarding.domain.user.dto.SignupRequestDto;
import com.sparta.onboarding.domain.user.entity.User;
import com.sparta.onboarding.domain.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret.key}")
    private String ADMIN_TOKEN;

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String userId = requestDto.getUserId();
        String password = requestDto.getPassword();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }


        if (!isValidUsername(username)) {
            throw new CustomException(ErrorCode.INVALID_USERNAME);
        }

        if (!isValidPassword(password)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }


        UserRole role = UserRole.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new CustomException(ErrorCode.INVALID_ADMIN_TOKEN);
            }
            role = UserRole.ADMIN;
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, userId, role);
        userRepository.save(user);
    }

    public User login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return user;
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 4 && username.length() <= 10 && username.matches("^[a-z0-9]*$");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.length() <= 15 && password.matches("^[a-zA-Z0-9]*$");
    }
}
