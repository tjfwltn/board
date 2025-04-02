package com.example.board.controller;

import com.example.board.dto.*;
import com.example.board.entity.Post;
import com.example.board.service.CommentService;
import com.example.board.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post API")
@RestController
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @Operation(summary = "게시글을 등록하는 API입니다.", description = "제목과 내용을 받아서 DB에 등록한다")
    @PostMapping("/posts")
    public PostResponse post(@RequestBody PostRequest request) {
        return postService.save(request);
    }

    @GetMapping("/posts/{id}")
    public PostResponse getPost(@PathVariable Long id,
                                @RequestParam(defaultValue = "createdAt") String sortType) {
        return postService.getPost(id, sortType);
    }

    @Operation(summary = "게시글 페이징 API", description = "인자로 페이지를 받아 최신순으로 20개씩 가져온다")
    @GetMapping("/posts/lists")
    public PageResponse getPostList(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size) {
        return postService.getPostList(page, size);
    }

    @DeleteMapping("/posts/{id}")
    public PostResponse deletePost(@PathVariable Long id) {
        return postService.deleteById(id);
    }

    @PatchMapping("/posts/{id}")
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostRequest request) {
        return postService.modify(id, request);
    }

    @PostMapping("/posts/{id}/comments")
    public CommentResponse addComment(@PathVariable Long id, @RequestBody CommentRequest request) {
        return commentService.create(id, request);
    }

//
//    @GetMapping("/posts/daily-best")
//    public List<PostResponse> getDailyBestPosts() {
//
//    }


}