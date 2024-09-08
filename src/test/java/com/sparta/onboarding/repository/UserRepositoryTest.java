package com.sparta.onboarding.repository;

import com.sparta.onboarding.domain.user.UserRepository;
import com.sparta.onboarding.domain.user.entity.User;
import com.sparta.onboarding.domain.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // DB 관련된 빈들만 로드, 임베디드 DB 사용
public class UserRepositoryTest {

    @Autowired // 의존성 주입
    private UserRepository userRepository;

    @BeforeEach // 메서드 실행 전 호출되는 메서드(테스트 데이터 초기화 위해)
    void setUp() {
        userRepository.deleteAll(); // 시작 전 데이터 삭제
    }

    @Test
    void testFindByUsernameWhenUserExists() {
        // Given (User 객체 생성, DB에 저장)
        User user = User.builder()
                .username("testUser")
                .password("password123")
                .userId("user1234")
                .role(UserRole.USER)
                .build();
        userRepository.save(user);

        // When (사용자 조회)
        Optional<User> foundUser = userRepository.findByUsername("testUser");

        // Then
        assertThat(foundUser).isPresent(); // 조회된 결과 존재하는지
        assertThat(foundUser.get().getUsername()).isEqualTo("testUser");
        assertThat(foundUser.get().getPassword()).isEqualTo("password123");
        assertThat(foundUser.get().getUserId()).isEqualTo("user1234");
        assertThat(foundUser.get().getRole()).isEqualTo(UserRole.USER);
    }

    @Test
    void testFindByUsernameWhenUserDoesNotExist() {
        // When
        Optional<User> foundUser = userRepository.findByUsername("nonexistentUser");

        // Then
        assertThat(foundUser).isNotPresent(); // 조회된 결과 존재하지 않아야 한다
    }
}
