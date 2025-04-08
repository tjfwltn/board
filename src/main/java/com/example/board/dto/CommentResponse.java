package com.example.board.dto;

import com.example.board.entity.Comment;
import com.example.board.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private String text;
    private String userName;
    private LocalDateTime createdAt;
    private List<CommentResponse> replies;

    public static CommentResponse fromComment(Comment comment, User user) {
        List<CommentResponse> replies = Optional.ofNullable(comment.getReplies())
                .orElse(Collections.emptyList())
                .stream()
                .map(reply -> CommentResponse.fromComment(reply, reply.getUser()))
                .collect(Collectors.toList());

        return new CommentResponse(comment.getText(), user.getUsername(), comment.getCreatedAt(), replies);
    }

    public int getRepliesSize() {
        return this.replies.size();
    }

}
