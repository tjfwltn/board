package com.example.board.service;

import com.example.board.converter.CommentConverter;
import com.example.board.dto.CommentRequest;
import com.example.board.dto.CommentResponse;
import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.entity.User;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.board.converter.CommentConverter.buildCommentHierarchy;

@Service
@Transactional
public class CommentService {

    private static final Map<String, Sort> SORT_MAP = Map.of(
            "latest", Sort.by(Sort.Direction.DESC, "createAt"),
            "replies", Sort.by(Sort.Direction.DESC, "parentId", "createdAt")
    );

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public CommentResponse create(Long id, CommentRequest request) {
        if (!userRepository.existsById(request.getUserId())) {
            throw new IllegalArgumentException("유저가 존재하지 않음");
        }
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("게시글이 지워졌습니다.");
        }

        User user = userRepository.getReferenceById(request.getUserId());
        Post post = postRepository.getReferenceById(id);

        Comment parent = null;

        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId()).get();
        }

        Comment comment = CommentConverter.toEntity(request, user, post, parent);
        commentRepository.save(comment);

        return CommentResponse.fromComment(comment, user);
    }

    public List<CommentResponse> getComments(Long postId) {
        List<Comment> comments = commentRepository.findAllCommentsByPostIdWithReplies(postId);
        return buildCommentHierarchy(comments);
//        List<Comment> parentComments = commentRepository.findParentCommentsByPostId(postId);
//
//        for (Comment comment : parentComments) {
//            List<Comment> childCommentsByParentId = commentRepository.findChildCommentsByParentId(comment.getId());
//            comment.getReplies().addAll(childCommentsByParentId);
//        }
//
//        return parentComments.stream()
//                .map(comment -> CommentResponse.fromComment(comment, comment.getUser()))
//                .toList();
    }


    private Sort getSortType(String sort) {
        return SORT_MAP.getOrDefault(sort, Sort.by(Sort.Direction.ASC, "createdAt"));
    }
}
