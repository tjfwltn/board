package com.example.board.dto;

import com.example.board.entity.Comment;
import com.example.board.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private String text;
    private String userName;
    private LocalDateTime createdAt;
    private List<CommentResponse> replies;

    public static CommentResponse fromComment(Comment comment, User user) {
        List<CommentResponse> replies = comment.getReplies().stream()
                .map(reply -> CommentResponse.fromComment(reply, reply.getUser()))
                .collect(Collectors.toList());
        return new CommentResponse(comment.getText(), user.getUsername(), comment.getCreatedAt(), replies);
    }
}
