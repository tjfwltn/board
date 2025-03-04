package com.example.board.service;

import com.example.board.dto.RecommendRequest;
import com.example.board.entity.Post;
import com.example.board.entity.Recommend;
import com.example.board.entity.User;
import com.example.board.repository.PostRepository;
import com.example.board.repository.RecommendRepository;
import com.example.board.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class RecommendService {

    private final RecommendRepository recommendRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public RecommendService(RecommendRepository recommendRepository, UserRepository userRepository, PostRepository postRepository) {
        this.recommendRepository = recommendRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public String recommend(Long postId, RecommendRequest recommendRequest) {
        User user = userRepository.findById(recommendRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        LocalDateTime now = LocalDateTime.now();
        boolean alreadyRecommended = recommendRepository.existsByUserIdAndPostIdAndRecommendDate(recommendRequest.getUserId(), postId, now);
        if (alreadyRecommended) {
            return "하루에 한 번만 추천할 수 있습니다.";
        }

        Recommend recommend = Recommend.builder()
                .recommendDate(now)
                .user(user)
                .post(post)
                .build();

        postRepository.increaseRecommendCount(postId);
        recommendRepository.save(recommend);
        return "추천이 완료되었습니다.";
    }

    public String delete(Long postId, RecommendRequest recommendRequest) {
        Recommend recommend = recommendRepository.findByUserIdAndPostId(postId, recommendRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("추천한 적 없음."));

        recommendRepository.delete(recommend);
        postRepository.decreaseRecommendCount(postId);

        return "추천이 취소되었습니다.";
    }
}
