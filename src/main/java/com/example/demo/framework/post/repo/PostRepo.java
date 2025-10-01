package com.example.demo.framework.post.repo;

import com.example.demo.framework.post.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findAllByOrderByCreatedAtDesc();

        Optional<PostEntity> findBySlug(String Slug);

    
    @Query("SELECT p FROM PostEntity p LEFT JOIN FETCH p.components WHERE p.id = :id")
    Optional<PostEntity> findByIdWithComponents(@Param("id") Long id);

}
