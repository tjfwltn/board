package com.example.board.dto;

import com.example.board.entity.User;
import lombok.Getter;

@Getter
public class PostRequest {
    private String title;
    private String content;
    private User user;
}
