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
                .build();
    }

    public static Post toUpdateEntity(Post post, PostRequest request) {
        String title = post.getTitle();
        String content = post.getContent();

        if (!request.getTitle().isEmpty()) {
            title = request.getTitle();
        }

        if (!request.getContent().isEmpty()) {
            content = request.getContent();
        }

        return Post.builder()
                .id(post.getId())
                .title(title)
                .content(content)
                .createdAt(post.getCreatedAt())
                .viewCount(post.getViewCount())
                .build();
    }
}
