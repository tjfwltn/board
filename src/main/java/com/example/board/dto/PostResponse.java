package com.example.board.dto;

import com.example.board.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private String message;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public PostResponse(String message) {
        this.message = message;
    }

    public static PostResponse fromPost(String message, Post post) {
        return new PostResponse(message, post.getTitle(), post.getContent(), LocalDateTime.now());
    }


    public static PostResponse delete(String message) {
        return new PostResponse(message);
    }
}
