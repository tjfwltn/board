package com.example.board.dto;

import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private String message;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String username;
    private List<CommentResponse> commentList;

    public PostResponse(String message) {
        this.message = message;
    }

    public PostResponse(String title, LocalDateTime createdAt, String username) {
        this.title = title;
        this.createdAt = createdAt;
        this.username = username;
    }

    public static PostResponse fromPost(String message, Post post, List<Comment> comments) {
        List<CommentResponse> commentResponses = comments.stream()
                .map(comment -> CommentResponse.fromComment(comment, comment.getUser()))
                .toList();

        return new PostResponse(message, post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUser().getUsername(), commentResponses);
    }

    public static PostResponse fromPostSummary(Post post) {
        return new PostResponse(
                post.getTitle(),
                post.getCreatedAt(),
                post.getUser().getUsername()
        );
    }

    public static PostResponse delete(String message) {
        return new PostResponse(message);
    }
}
