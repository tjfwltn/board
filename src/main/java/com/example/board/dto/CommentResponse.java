package com.example.board.dto;

import com.example.board.entity.Comment;
import com.example.board.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private String message;
    private String text;
    private String userName;
    private LocalDateTime createdAt;

    public static CommentResponse fromComment(String message, Comment comment, User user) {
        return new CommentResponse(message, comment.getText(), user.getUsername(), LocalDateTime.now());
    }
}
