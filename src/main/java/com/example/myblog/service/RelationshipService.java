package com.example.myblog.service;

import com.example.myblog.model.Relationship;

import java.util.List;

public interface RelationshipService {
    List<Relationship> findAll();

    List<Relationship> findRelationshipsByFollowerIdOrderById(int id);

    List<Relationship> findRelationshipsByFollowedIdOrderById(int id);

    void save(Relationship relationship);

    void deleteById(int id);

}
