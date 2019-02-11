package com.example.myblog.repository;

import com.example.myblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query("SELECT p FROM Post p WHERE p.user.id = :id ORDER BY p.id DESC")
    List<Post> findPostsByUserIDAndSortDESC(@Param("id") int id);

    List<Post> findPostsByUserId(int id);

}
