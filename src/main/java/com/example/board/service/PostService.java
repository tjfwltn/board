package com.example.board.service;

import com.example.board.dto.PostRequest;
import com.example.board.dto.PostResponse;
import com.example.board.entity.Post;
import com.example.board.repository.PostRepository;
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
        Post post = postRepository.save(Post.of(request));
        String message = "저장이 성공적으로 이루어짐";

        return PostResponse.fromPost(message, post);
    }
    public PostResponse getPost(Long postId) {
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
        // title이 null일 경우
        // content가 null일 경우
        // 둘다 null일 경우

        Post updatePost = postRepository.updatePost(postId, request.getTitle(), request.getContent());
        String message = "수정 완료";

        return PostResponse.fromPost(message, updatePost);
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(""));
    }
}
