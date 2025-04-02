package com.example.board.converter;


import com.example.board.constants.SortType;
import com.example.board.dto.CommentRequest;
import com.example.board.dto.CommentResponse;
import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.entity.User;

import java.time.LocalDateTime;
import java.util.*;

public final class CommentConverter {

    private static final List<String> SORT_TYPES = List.of("createdAt", "latest", "replies");

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

    public static List<CommentResponse> buildCommentHierarchy(List<Comment> comments, String sortType) {
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
        Comparator<CommentResponse> comparator = getComparator(sortType);

        System.out.println("sortType = " + sortType);
        System.out.println("comparator = " + comparator);
        return rootComments.stream()
                .sorted(comparator).toList();

    }

    public static Comparator<CommentResponse> getComparator(String sortType) {
        SortType type = SortType.fromString(sortType);
        System.out.println("Comparator 사용 : " + type);
        return switch (type) {
            case CREATED_AT -> Comparator.comparing(CommentResponse::getCreatedAt);
            case LATEST -> Comparator.comparing(CommentResponse::getCreatedAt).reversed();
            case MOST_REPLIES -> Comparator.comparingInt(CommentResponse::getRepliesSize)
                    .reversed();
        };
    }
}
