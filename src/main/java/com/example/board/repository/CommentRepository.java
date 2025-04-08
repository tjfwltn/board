package com.example.board.repository;

import com.example.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
    SELECT DISTINCT c
    FROM Comment c
    LEFT JOIN FETCH c.user
    LEFT JOIN FETCH c.replies r
    LEFT JOIN FETCH r.user
    WHERE c.post.id = :postId
""")
    List<Comment> findAllCommentsWithReplies(@Param("postId") Long postId);

    // 재귀 쿼리
//    @Query(value = """
//        WITH RECURSIVE CommentTree AS (
//            -- 최상위 댓글 조회 (부모 댓글)
//            SELECT
//                c.id, c.text, c.created_at, c.parent_id, c.post_id, c.user_id,
//                0 AS depth,
//                CAST(c.id AS CHAR(255)) AS sort_order,
//                (SELECT COUNT(*) FROM comment c2 WHERE c2.parent_id = c.id) AS reply_count
//            FROM comment c
//            WHERE c.post_id = :postId AND c.parent_id IS NULL
//
//            UNION ALL
//
//            -- 대댓글(자식 댓글) 조회
//            SELECT
//                c.id, c.text, c.created_at, c.parent_id, c.post_id, c.user_id,
//                ct.depth + 1,
//                CONCAT(ct.sort_order, '-', c.id) AS sort_order,
//                (SELECT COUNT(*) FROM comment c2 WHERE c2.parent_id = c.id) AS reply_count
//            FROM comment c
//            INNER JOIN CommentTree ct ON c.parent_id = ct.id
//        )
//        SELECT * FROM CommentTree
//        ORDER BY
//            sort_order ASC
//        """, nativeQuery = true)
//    List<Comment> findCommentsByPostId(@Param("postId") Long postId);
}
