package com.sparta.onboarding.domain.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleRequestDto {
    private String title;
    private String content;
    private String password;
    private String username;
}
