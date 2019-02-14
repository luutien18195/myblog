package com.example.myblog.service.impl;

import com.example.myblog.model.Relationship;
import com.example.myblog.repository.RelationshipRepository;
import com.example.myblog.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipServiceImpl implements RelationshipService {
    private RelationshipRepository relationshipRepository;

    @Autowired
    public RelationshipServiceImpl(RelationshipRepository relationshipRepository){
        this.relationshipRepository = relationshipRepository;
    }

    @Override
    public List<Relationship> findAll() {
        return this.relationshipRepository.findAll();
    }

    @Override
    public List<Relationship> findRelationshipsByFollowerIdOrderById(int id) {
        return this.relationshipRepository.findRelationshipsByFollowerIdOrderById(id);
    }

    @Override
    public List<Relationship> findRelationshipsByFollowedIdOrderById(int id) {
        return this.relationshipRepository.findRelationshipsByFollowedIdOrderById(id);
    }

    @Override
    public void save(Relationship relationship) {
        this.relationshipRepository.save(relationship);
    }

    @Override
    public void deleteById(int id) {
        this.relationshipRepository.deleteById(id);
    }
}
