package com.sparta.onboarding.domain.comment.dto;

import lombok.Getter;

@Getter
public class CommentUpdateRequest {

    private String username;
    private String content;

    public CommentUpdateRequest(String username, String content) {
        this.username = username;
        this.content = content;
    }
}