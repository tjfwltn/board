package com.example.board.dto;

import lombok.Getter;

@Getter
public class CommentRequest {
    private Long userId;
    private String text;
    private Long parentId;
}
