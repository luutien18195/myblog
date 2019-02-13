package com.example.myblog.repository;

import com.example.myblog.model.Relationship;
import com.example.myblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {

    @Query("SELECT r FROM Relationship r WHERE r.followed.id = :id OR r.follower.id = :id ORDER BY r.id DESC")
    Relationship findRelationshipsByFollowedOrFollowerOrderById(@Param("id") int id);

    @Query("SELECT r FROM Relationship r WHERE r.follower.id = :id ORDER BY r.id DESC")
    Relationship findRelationshipsByFollowerIdOrderById(@Param("id") int id);

    @Query("SELECT r FROM Relationship r WHERE r.followed.id = :id ORDER BY r.id DESC")
    Relationship findRelationshipsByFollowedIdOrderById(@Param("id") int id);
}
