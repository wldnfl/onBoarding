package com.sparta.onboarding.domain.comment;

import com.sparta.onboarding.common.exception.CustomException;
import com.sparta.onboarding.common.exception.ErrorCode;
import com.sparta.onboarding.domain.comment.dto.CommentRequestDto;
import com.sparta.onboarding.domain.comment.dto.CommentResponseDto;
import com.sparta.onboarding.domain.comment.dto.CommentUpdateRequest;
import com.sparta.onboarding.domain.schedule.Schedule;
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

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto request, long scheduleId, String username) {
        // DB에 일정이 존재하지 않는 경우
        Schedule schedule = scheduleService.findScheduleById(scheduleId);
        Comment comment = new Comment(request.getComment(), username, schedule);
        comment = commentRepository.save(comment);
        return CommentResponseDto.toDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(long scheduleId, long commentId, CommentUpdateRequest request, String username) {
        // DB에 일정이 존재하지 않는 경우
        scheduleService.findScheduleById(scheduleId);

        // 해당 댓글이 DB에 존재하지 않는 경우
        Comment comment = findById(commentId);

        // 사용자가 일치하지 않는 경우
        if (!Objects.equals(comment.getUsername(), username)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        comment.updateComment(request.getComment());
        return CommentResponseDto.toDto(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public void deleteComment(Long scheduleId, Long commentId, String username) {
        if (scheduleId == null || commentId == null) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        // DB에 일정이 존재하지 않는 경우
        scheduleService.findScheduleById(scheduleId);

        // 해당 댓글이 DB에 존재하지 않는 경우
        Comment comment = findById(commentId);

        if (!Objects.equals(comment.getUsername(), username)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        commentRepository.delete(comment);
    }
}
