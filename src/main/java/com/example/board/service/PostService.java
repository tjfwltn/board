package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.dto.PageResponse;
import com.example.board.dto.PostRequest;
import com.example.board.dto.PostResponse;
import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public PostResponse save(PostRequest request) {
        Post post = postRepository.save(PostConverter.toEntity(request));

        return PostResponse.fromPostSummary(post);
    }
    public PostResponse getPost(Long postId) {
        Post post = getPostById(postId);
        postRepository.increaseViewCount(postId);

        List<Comment> parentComments = commentRepository.findParentCommentsByPostId(postId);
        for (Comment parentComment : parentComments) {
            List<Comment> childComments = commentRepository.findChildCommentsByParentId(parentComment.getId());
            parentComment.addReplies(childComments);
        }
        return PostResponse.fromPost("조회 성공", post, parentComments);
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
        return PostResponse.fromPostSummary(updatedPost);
    }

    private Post getPostById(Long postId) {
        return postRepository.findByIdWithUser(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없거나 번호가 잘못되었습니다"));
    }

    public PageResponse getPostList(int page, int size) {
        PageRequest createdAt = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> posts = postRepository.findAllPostsWithUser(createdAt);

        return PageResponse.from(posts);
    }
}
