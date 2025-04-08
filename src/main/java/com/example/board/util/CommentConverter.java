package com.example.board.util;


import com.example.board.constants.SortType;
import com.example.board.dto.CommentRequest;
import com.example.board.dto.CommentResponse;
import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public static List<CommentResponse> buildCommentHierarchy(List<Comment> parentComments, List<Comment> childComments, String sortType) {
        List<CommentResponse> allComments = new ArrayList<>();

        for (Comment parentComment : parentComments) {
            CommentResponse parentResponse = CommentResponse.fromComment(parentComment, parentComment.getUser());

            List<CommentResponse> childResponse = childComments.stream()
                    .filter(child -> child.getParent().getId().equals(parentComment.getId()))
                    .map(child -> CommentResponse.fromComment(child, child.getUser()))
                    .toList();

            parentResponse.setReplies(childResponse);
            allComments.add(parentResponse);
        }

        return allComments.stream()
                .sorted(getComparator(sortType)).toList();
    }

    public static Comparator<CommentResponse> getComparator(String sortType) {
        SortType type = SortType.fromString(sortType);
        return switch (type) {
            case CREATED_AT -> Comparator.comparing(CommentResponse::getCreatedAt);
            case LATEST -> Comparator.comparing(CommentResponse::getCreatedAt).reversed();
            case MOST_REPLIES -> Comparator.comparing(CommentResponse::getRepliesSize)
                    .reversed();
        };
    }

    public static List<CommentResponse> buildCommentHierarchy(List<Comment> allCommentsWithReplies, String sortType) {
        return allCommentsWithReplies.stream()
                .filter(comment -> comment.getParent() == null)
                .map(comment -> CommentResponse.fromComment(comment, comment.getUser()))
                .sorted(getComparator(sortType))
                .toList();
    }
}
