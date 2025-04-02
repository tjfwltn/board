package com.example.board.converter;


import com.example.board.dto.CommentRequest;
import com.example.board.dto.CommentResponse;
import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static List<CommentResponse> buildCommentHierarchy(List<Comment> comments) {
        Map<Long, CommentResponse> commentMap = new HashMap<>();
        List<CommentResponse> rootComments = new ArrayList<>();

        for (Comment comment : comments) {
            commentMap.put(comment.getId(), CommentResponse.fromComment(comment, comment.getUser()));
        }

        for (Comment comment : comments) {
            CommentResponse current = commentMap.get(comment.getId());
            if (comment.getParent() == null) {
                rootComments.add(current);
            }
        }

        return rootComments;

    }

}
