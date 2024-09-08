package com.sparta.onboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.onboarding.domain.user.UserRepository;
import com.sparta.onboarding.domain.user.dto.LoginRequestDto;
import com.sparta.onboarding.domain.user.dto.SignupRequestDto;
import com.sparta.onboarding.domain.user.entity.User;
import com.sparta.onboarding.domain.user.entity.UserRole;
import com.sparta.onboarding.mock.WithCustomMockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Test data initialization
    }

    @Test
    @WithCustomMockUser(username = "testUser")
    void testLogin() throws Exception {
        User user = User.builder()
                .username("testUser")
                .password("password123")
                .userId("user1234")
                .role(UserRole.USER)
                .build();
        userRepository.save(user);

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username("testUser")
                .password("password123")
                .build();

        String content = objectMapper.writeValueAsString(loginRequestDto);

        ResultActions resultActions = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        resultActions.andExpect(status().isOk());
    }

    @Test
    @WithCustomMockUser(username = "newUser")
    void testSignup() throws Exception {
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .username("newUser")
                .password("password123")
                .userId("user1234")
                .build();

        String content = objectMapper.writeValueAsString(signupRequestDto);

        ResultActions resultActions = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        resultActions.andExpect(status().isCreated());
    }
}
