package com.example.demo.framework.post.service;

import com.example.demo.framework.post.dto.ComponentsRequest;
import com.example.demo.framework.post.dto.CreatePost;
import com.example.demo.framework.post.model.PostComponent;
import com.example.demo.framework.post.model.PostEntity;

import java.util.List;
import java.util.Optional;

public interface PostService {

    public List<PostEntity> getAllPosts();
    public Optional<PostEntity> getPostBySlug(String slug);
    public PostEntity getPostWithParsedComponents(Long id);
    public PostEntity createPost(CreatePost request);

    public PostComponent createComponent(PostEntity post, ComponentsRequest componentReq, int order) ;

    }
