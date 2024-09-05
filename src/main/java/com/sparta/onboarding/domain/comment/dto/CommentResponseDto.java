package com.sparta.onboarding.domain.comment.dto;

import com.sparta.onboarding.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String comment;
    private String username;
    private String createdAt;
    private String updatedAt;

    public CommentResponseDto(Long id, String comment, String username, String createdAt, String updatedAt) {
        this.id = id;
        this.comment = comment;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getComment(),
                comment.getUsername(),
                comment.getCreatedAt().toString(),
                comment.getUpdatedAt().toString()
        );
    }
}
