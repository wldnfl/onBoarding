package com.sparta.onboarding.domain.comment;

import com.sparta.onboarding.common.exception.CustomException;
import com.sparta.onboarding.common.exception.ErrorCode;
import com.sparta.onboarding.domain.comment.dto.CommentCreateRequest;
import com.sparta.onboarding.domain.comment.dto.CommentResponse;
import com.sparta.onboarding.domain.comment.dto.CommentUpdateRequest;
import com.sparta.onboarding.domain.schedule.Schedule;
import com.sparta.onboarding.domain.schedule.ScheduleRepository;
import com.sparta.onboarding.domain.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentResponse save(long scheduleId, CommentCreateRequest request) {
        // DB에 일정이 존재하지 않는 경우
        Schedule schedule = scheduleService.findScheduleById(scheduleId);
        Comment comment = commentRepository.save(new Comment(request.getContent(), request.getUsername(), schedule));
        return CommentResponse.toDto(comment);
    }

    @Transactional
    public CommentResponse update(long scheduleId, long commentId, CommentUpdateRequest request) {
        // DB에 일정이 존재하지 않는 경우
        scheduleService.findScheduleById(scheduleId);
        // 해당 댓글이 DB에 존재하지 않는 경우
        Comment comment = findById(commentId);

        // 사용자가 일치하지 않는 경우
        if (!Objects.equals(comment.getUsername(), request.getUsername())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        comment.update(request.getContent());
        return CommentResponse.toDto(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public void delete(Long scheduleId, Long commentId, String username) {
        if (scheduleId == null || commentId == null) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        // DB에 일정이 존재하지 않는 경우
        scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        // 해당 댓글이 DB에 존재하지 않는 경우
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!Objects.equals(comment.getUsername(), username)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        commentRepository.delete(comment);
    }
}
