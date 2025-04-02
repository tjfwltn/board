package com.example.board.converter;


import com.example.board.dto.CommentRequest;
import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.entity.User;

import java.time.LocalDateTime;

public final class CommentConverter {

    private CommentConverter() {
        throw new AssertionError("utility class");
    }


    public static Comment toEntity(CommentRequest request, User user, Post post, Comment parent) {
        if (request.getParentId() != null) {
            return Comment.builder()
                    .text(request.getText())
                    .createdAt(LocalDateTime.now())
                    .user(user)
                    .post(post)
                    .parent(parent)
                    .build();
        }

        return Comment.builder()
                .text(request.getText())
                .createdAt(LocalDateTime.now())
                .user(user)
                .post(post)
                .build();
    }
}
