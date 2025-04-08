package com.example.board.controller;

import com.example.board.dto.CommentRequest;
import com.example.board.dto.CommentResponse;
import com.example.board.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public CommentResponse addComment(@PathVariable Long postId, @RequestBody CommentRequest request) {
        return commentService.create(postId, request);
    }

    @Operation(summary = "댓글 정렬 API", description = "정렬 기준, 게시글 id를 인자로 받아 댓글을 정렬하는 기능")
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "0") int commentPage,
            @RequestParam(defaultValue = "50") int commentPageSize
            ) {
        List<CommentResponse> commentResponses = commentService.getComments(postId, sort, commentPage, commentPageSize);
        return ResponseEntity.ok(commentResponses);
    }
}
