package com.sparta.onboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OnBoardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnBoardingApplication.class, args);
    }

}
