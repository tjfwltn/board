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

    private String text;
    private String userName;
    private LocalDateTime createdAt;

    public static CommentResponse fromComment(Comment comment, User user) {
        return new CommentResponse(comment.getText(), user.getUsername(), comment.getCreatedAt());
    }
}
