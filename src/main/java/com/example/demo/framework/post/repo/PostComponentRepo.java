package com.example.demo.framework.post.repo;

import com.example.demo.framework.post.model.ComponentType;
import com.example.demo.framework.post.model.PostComponent;

import java.util.List;

public interface PostComponentRepo {

    List<PostComponent> findByPostIdOrderByComponentOrder(Long postId);

    List<PostComponent> findByPostIdAndComponentType(Long postId, ComponentType componentType);

    void deleteByPostIdAndComponentType(Long postId, ComponentType componentType);

}
