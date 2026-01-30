package com.example.demo.framework.post.repo;

import com.example.demo.framework.post.model.PostDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostMongoRepository
        extends MongoRepository<PostDocument, String> {

    List<PostDocument> findAllByOrderByCreatedAtDesc();

    Optional<PostDocument> findBySlug(String slug);
}

