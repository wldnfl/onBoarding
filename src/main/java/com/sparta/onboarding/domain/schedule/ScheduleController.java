package com.sparta.onboarding.domain.schedule;

import com.sparta.onboarding.domain.schedule.dto.ScheduleResponse;
import com.sparta.onboarding.domain.schedule.dto.ScheduleRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Operation(summary = "Create Schedule", description = "일정 생성 기능입니다.")
    @PostMapping("/schedules")
    public ScheduleResponse createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.createSchedule(scheduleRequestDto);
    }

    @Operation(summary = "Read Schedule", description = "일정 목록 조회 기능입니다.")
    @GetMapping("/schedules")
    public List<ScheduleResponse> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @Operation(summary = "Read Schedule", description = "선택 일정 조회 기능입니다.")
    @GetMapping("/schedules/{id}")
    public ScheduleResponse getScheduleById(@PathVariable Long id) {
        return scheduleService.getSelectedSchedule(id);
    }

    @Operation(summary = "Update Schedule", description = "일정 수정 기능입니다.")
    @PutMapping("/schedules/{id}")
    public ScheduleResponse updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);
    }

    @Operation(summary = "Delete Schedule", description = "선택 일정 삭제 기능입니다.")
    @DeleteMapping("/schedules/{id}")
    public void deleteSchedule(@PathVariable Long id, @RequestParam String password) {
        scheduleService.deleteSchedule(id, password);
    }

}
