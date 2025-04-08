package com.example.board.service;

import com.example.board.dto.CommentRequest;
import com.example.board.dto.CommentResponse;
import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.entity.User;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import com.example.board.util.CommentConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class CommentService {

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

    public List<CommentResponse> getComments(Long postId, String sortType, int commentPage, int commentPageSize) {
        List<Comment> allCommentsWithReplies = commentRepository.findAllCommentsWithReplies(postId);
        List<CommentResponse> commentHierarchy = CommentConverter.buildCommentHierarchy(allCommentsWithReplies, sortType);

        int fromIndex = commentPage * commentPageSize;
        int toIndex = Math.min(fromIndex + commentPageSize, commentHierarchy.size());

        if (fromIndex >= commentHierarchy.size()) {
            return Collections.emptyList();
        }

        return commentHierarchy.subList(fromIndex, toIndex);
    }

}
