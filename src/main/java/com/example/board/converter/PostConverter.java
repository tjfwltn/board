package com.example.board.converter;

import com.example.board.dto.PostRequest;
import com.example.board.entity.Post;

import java.time.LocalDateTime;

public final class PostConverter {

    private PostConverter() {
        throw new AssertionError("utility class");
    }

    public static Post toEntity(PostRequest dto) {
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .user(dto.getUser())
                .viewCount(0L)
                .recommendCount(0)
                .build();
    }

    public static Post toUpdateEntity(Post post, PostRequest request) {
        String title = post.getTitle();
        String content = post.getContent();

        if (request.getTitle() != null) {
            title = request.getTitle();
        }

        if (request.getContent() != null) {
            content = request.getContent();
        }

        return Post.builder()
                .id(post.getId())
                .title(title)
                .content(content)
                .createdAt(post.getCreatedAt())
                .viewCount(post.getViewCount())
                .recommendCount(post.getRecommendCount())
                .user(post.getUser())
                .build();
    }
}
