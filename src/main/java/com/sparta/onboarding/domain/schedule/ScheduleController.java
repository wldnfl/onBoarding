package com.sparta.onboarding.domain.schedule;

import com.sparta.onboarding.domain.schedule.dto.ScheduleResponse;
import com.sparta.onboarding.domain.schedule.dto.ScheduleRequestDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedules") // 일정 등록
    public ScheduleResponse createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.createSchedule(scheduleRequestDto);
    }

    @GetMapping("/schedules") // 일정 목록 조회
    public List<ScheduleResponse> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/schedules/{id}") // 선택 일정 조회
    public ScheduleResponse getScheduleById(@PathVariable Long id) {
        return scheduleService.getSelectedSchedule(id);
    }

    @PutMapping("/schedules/{id}") // 선택 일정 수정
    public ScheduleResponse updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);
    }

    @DeleteMapping("/schedules/{id}") // 선택 일정 삭제
    public void deleteSchedule(@PathVariable Long id, @RequestParam String password) {
        scheduleService.deleteSchedule(id, password);
    }

}
