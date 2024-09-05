package com.sparta.onboarding.domain.comment;

import com.sparta.onboarding.common.response.DataCommonResponse;
import com.sparta.onboarding.common.response.StatusCommonResponse;
import com.sparta.onboarding.domain.comment.dto.CommentRequestDto;
import com.sparta.onboarding.domain.comment.dto.CommentResponseDto;
import com.sparta.onboarding.domain.comment.dto.CommentUpdateRequest;
import com.sparta.onboarding.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Create Comment", description = "댓글 생성 기능입니다.")
    @PostMapping
    public ResponseEntity<DataCommonResponse<CommentResponseDto>> createComment(
            @PathVariable Long scheduleId,
            @Validated @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, scheduleId, userDetails.getUsername());
        DataCommonResponse<CommentResponseDto> response = new DataCommonResponse<>(
                HttpStatus.CREATED.value(),
                "댓글이 성공적으로 생성되었습니다.",
                responseDto
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Update Comment", description = "댓글 수정 기능입니다.")
    @PatchMapping("/{commentId}")
    public ResponseEntity<DataCommonResponse<CommentResponseDto>> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Validated @RequestBody CommentUpdateRequest commentUpdateRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.updateComment(scheduleId, commentId, commentUpdateRequest, userDetails.getUsername());
        DataCommonResponse<CommentResponseDto> response = new DataCommonResponse<>(
                HttpStatus.OK.value(),
                "댓글이 성공적으로 수정되었습니다.",
                responseDto
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete Comment", description = "댓글 삭제 기능입니다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<StatusCommonResponse> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody String username) {
        commentService.deleteComment(scheduleId, commentId, username);
        StatusCommonResponse response = new StatusCommonResponse(
                HttpStatus.OK.value(),
                "댓글이 성공적으로 삭제되었습니다."
        );
        return ResponseEntity.ok(response);
    }
}
