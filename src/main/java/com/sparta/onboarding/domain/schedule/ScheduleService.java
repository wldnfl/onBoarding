package com.sparta.onboarding.domain.schedule;

import com.sparta.onboarding.common.exception.CustomException;
import com.sparta.onboarding.common.exception.ErrorCode;
import com.sparta.onboarding.domain.schedule.dto.ScheduleRequestDto;
import com.sparta.onboarding.domain.schedule.dto.ScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponse createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(
                scheduleRequestDto.getTitle(),
                scheduleRequestDto.getContent(),
                scheduleRequestDto.getPassword(),
                scheduleRequestDto.getUsername(),
                LocalDateTime.now()
        );
        scheduleRepository.save(schedule);
        return ScheduleResponse.toDto(schedule);
    }

    public List<ScheduleResponse> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(ScheduleResponse::toDto)
                .collect(Collectors.toList());
    }

    public ScheduleResponse getSelectedSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
        return ScheduleResponse.toDto(schedule);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }
        schedule.update(requestDto);
        return ScheduleResponse.toDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (!schedule.getPassword().equals(password)) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        if (schedule.isDeleted()) {
            throw new CustomException(ErrorCode.SCHEDULE_ALREADY_DELETED);
        }

        schedule.markAsDeleted();
        scheduleRepository.save(schedule);
    }

    public Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
    }
}
