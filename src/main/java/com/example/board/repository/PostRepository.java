package com.example.board.repository;

import com.example.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void increaseViewCount(@Param("id") Long postId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Post p SET p.recommendCount = p.recommendCount + 1 WHERE p.id = :id")
    void increaseRecommendCount(@Param("id") Long postId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Post p SET p.recommendCount = p.recommendCount - 1 WHERE p.id = :id")
    void decreaseRecommendCount(@Param("id") Long postId);

    @Query("SELECT p from Post p JOIN FETCH p.user ORDER BY p.createdAt DESC")
    Page<Post> findAllPostsWithUser(Pageable pageable);

    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.id = :postId")
    Optional<Post> findByIdWithUser(@Param("postId") Long postId);

}
