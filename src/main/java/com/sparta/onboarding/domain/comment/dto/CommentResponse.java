package com.sparta.onboarding.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.onboarding.domain.comment.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Long scheduleId;

    public CommentResponse(Long id, String content, String username, Long scheduleId, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.scheduleId = scheduleId;
        this.createdAt = createdAt;
    }

    public static CommentResponse toDto(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUsername(),
                comment.getSchedule().getId(),
                comment.getCreatedAt()
        );
    }
}