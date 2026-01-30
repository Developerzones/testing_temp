package com.example.demo.framework.post.repo;

import com.example.demo.framework.post.model.PostEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PostRepository extends MongoRepository<PostEntity, String> {
    Optional<PostEntity> findBySlug(String slug);
}
