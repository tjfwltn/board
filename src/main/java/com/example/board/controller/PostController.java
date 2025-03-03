package com.example.board.controller;

import com.example.board.dto.PostRequest;
import com.example.board.dto.PostResponse;
import com.example.board.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post API")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "게시글을 등록하는 API입니다.", description = "제목과 내용을 받아서 DB에 등록한다")
    @PostMapping("/posts")
    public PostResponse post(@RequestBody PostRequest request) {
        return postService.save(request);
    }

    @GetMapping("/posts/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @DeleteMapping("/posts/{id}")
    public PostResponse deletePost(@PathVariable Long id) {
        return postService.deleteById(id);
    }

    @PatchMapping("/posts/{id}")
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostRequest request) {
        return postService.modify(id, request);
    }

    @GetMapping("/posts/{id}/recommend")
    public PostResponse recommend(@PathVariable Long id) {
        return postService.increaseRecommendCount(id);
    }

}