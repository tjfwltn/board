package com.example.board.repository;

import com.example.board.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    boolean existsByUserIdAndPostIdAndRecommendDate(Long userId, Long postId, LocalDate recommendDate);

    Optional<Recommend> findByUserIdAndPostId(Long userId, Long postId);
}
