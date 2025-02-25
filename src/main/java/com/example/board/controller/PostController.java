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

    // post에 조회 수라는 column 생성해
    // get을 불렀을 때 하나가 올라가게
    // 댓글 post 외래 키 댓글 외래키로 ?
    // 추천
    // 검색을 할 때 자동 완성 기능 ?
    // trie - app
    
}