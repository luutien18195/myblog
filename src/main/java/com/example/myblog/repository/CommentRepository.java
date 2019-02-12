package com.example.myblog.repository;

import com.example.myblog.model.Comment;
import com.example.myblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c WHERE c.post.id = :id ORDER BY c.id DESC")
    List<Comment> findCommentsByPostAndSortById(@Param("id") int id);

    @Query("SELECT c FROM Comment c ORDER BY c.id DESC")
    List<Comment> findAllAndSortById();
}
