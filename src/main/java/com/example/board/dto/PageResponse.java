package com.example.board.dto;

import com.example.board.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse {

    private List<PostResponse> contents;
    private int pageNum;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public static PageResponse from(Page<Post> postPage) {
        List<PostResponse> postResponses = postPage.getContent().stream()
                .map(PostResponse::fromPostSummary)
                .collect(Collectors.toList());

        return new PageResponse(
                postResponses,
                postPage.getNumber(),
                postPage.getSize(),
                postPage.getTotalElements(),
                postPage.getTotalPages()
        );
    }
}
