package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.dto.PageResponse;
import com.example.board.dto.PostRequest;
import com.example.board.dto.PostResponse;
import com.example.board.entity.Post;
import com.example.board.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponse save(PostRequest request) {
        Post post = postRepository.save(PostConverter.toEntity(request));
        String message = "저장이 성공적으로 이루어짐";

        return PostResponse.fromPost(message, post);
    }
    public PostResponse getPost(Long postId) {
        postRepository.increaseViewCount(postId);
        Post post = getPostById(postId);
        return PostResponse.fromPost("조회 성공", post);
    }

    public PostResponse deleteById(Long postId) {
        Post post = getPostById(postId);
        postRepository.delete(post);
        String message = "삭제 완료";

        return PostResponse.delete(message);
    }

    public PostResponse modify(Long postId, PostRequest request) {
        Post post = getPostById(postId);
        Post updatedPost = PostConverter.toUpdateEntity(post, request);

        postRepository.save(updatedPost);
        String message = "수정 완료";

        return PostResponse.fromPost(message, updatedPost);
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없거나 번호가 잘못되었습니다"));
    }

    public PageResponse getPostList(int page, int size) {
        PageRequest createdAt = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> posts = postRepository.findAll(createdAt);

        return PageResponse.from(posts);
    }
}
