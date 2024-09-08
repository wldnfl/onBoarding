package com.sparta.onboarding.service;

import com.sparta.onboarding.common.exception.CustomException;
import com.sparta.onboarding.common.exception.ErrorCode;
import com.sparta.onboarding.domain.user.UserRepository;
import com.sparta.onboarding.domain.user.UserService;
import com.sparta.onboarding.domain.user.dto.LoginRequestDto;
import com.sparta.onboarding.domain.user.dto.SignupRequestDto;
import com.sparta.onboarding.domain.user.entity.User;
import com.sparta.onboarding.domain.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("testUser", "encodedPassword", "user1234", UserRole.USER);
    }

    @Test
    void registerUserWhenUsernameExists() {
        // Given
        SignupRequestDto requestDto = SignupRequestDto.builder()
                .username("testUser")
                .password("password123")
                .userId("user1234")
                .isAdmin(false)
                .adminToken(null)
                .build();
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // When & Then
        assertThatThrownBy(() -> userService.registerUser(requestDto))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USERNAME_ALREADY_EXISTS);
    }

    @Test
    void registerUserWhenInvalidUsername() {
        // Given
        SignupRequestDto requestDto = SignupRequestDto.builder()
                .username("inv")
                .password("password123")
                .userId("user1234")
                .isAdmin(false)
                .adminToken(null)
                .build();

        // When & Then
        assertThatThrownBy(() -> userService.registerUser(requestDto))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_USERNAME);
    }

    @Test
    void registerUserWhenInvalidPassword() {
        // Given
        SignupRequestDto requestDto = SignupRequestDto.builder()
                .username("validUser")
                .password("short")
                .userId("user1234")
                .isAdmin(false)
                .adminToken(null)
                .build();

        // When & Then
        assertThatThrownBy(() -> userService.registerUser(requestDto))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_PASSWORD);
    }

    @Test
    void registerUserWhenAdminTokenInvalid() {
        // Given
        SignupRequestDto requestDto = SignupRequestDto.builder()
                .username("adminUser")
                .password("validPassword123")
                .userId("user1234")
                .isAdmin(true)
                .adminToken("wrongToken")
                .build();
        when(userRepository.findByUsername("adminUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("validPassword123")).thenReturn("encodedPassword");

        // When & Then
        assertThatThrownBy(() -> userService.registerUser(requestDto))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_ADMIN_TOKEN);
    }

    @Test
    void registerUserSuccessfully() {
        // Given
        SignupRequestDto requestDto = SignupRequestDto.builder()
                .username("newUser")
                .password("validPassword123")
                .userId("user1234")
                .isAdmin(false)
                .adminToken(null)
                .build();
        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("validPassword123")).thenReturn("encodedPassword");

        // When
        userService.registerUser(requestDto);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void loginWhenUserNotFound() {
        // Given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username("nonExistentUser")
                .password("password123")
                .build();
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.login(loginRequestDto))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_NOT_FOUND);
    }

    @Test
    void loginWhenPasswordInvalid() {
        // Given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username("testUser")
                .password("wrongPassword")
                .build();
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq("wrongPassword"), any(String.class))).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> userService.login(loginRequestDto))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_PASSWORD);
    }

    @Test
    void loginSuccessfully() {
        // Given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username("testUser")
                .password("correctPassword")
                .build();
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq("correctPassword"), any(String.class))).thenReturn(true);

        // When
        User loggedInUser = userService.login(loginRequestDto);

        // Then
        assertThat(loggedInUser).isEqualTo(user);
    }
}
