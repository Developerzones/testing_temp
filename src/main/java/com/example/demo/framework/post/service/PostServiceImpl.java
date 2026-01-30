package com.example.demo.framework.post.service;

import com.example.demo.framework.post.dto.ComponentsRequest;
import com.example.demo.framework.post.dto.CreatePost;
import com.example.demo.framework.post.model.PostComponent;
import com.example.demo.framework.post.model.PostEntity;
import com.example.demo.framework.post.repo.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<PostEntity> getPostBySlug(String slug) {
        return postRepository.findBySlug(slug);
    }

    @Override
    public PostEntity getPostWithParsedComponents(Long id) {
        // Optional: implement if needed, otherwise return null or throw exception
        return null;
    }

    @Override
    public PostEntity createPost(CreatePost request) {
        PostEntity post = new PostEntity();
        post.setHeadingText(request.getHeadingText());
        post.setAuthorName(request.getAuthorName());
        post.setCategory(request.getCategory());
        post.setSlug(request.getSlug());
        post.setCreatedAt(LocalDateTime.now());

        // Convert ComponentsRequest -> PostComponent
        List<PostComponent> components = new ArrayList<>();
        if (request.getComponents() != null) {
            int order = 1;
            for (ComponentsRequest compReq : request.getComponents()) {
                PostComponent component = new PostComponent();
                component.setComponentType(compReq.getType().toString()); // enum -> string
                component.setComponentOrder(order++);

                // Assign data depending on component type
                switch (compReq.getType()) {
                    case POST_BODY -> component.setComponentData(compReq.getContent());
                    case CODE_BLOCK_WITH_COPY -> component.setComponentData(compReq.getCode());
                    case IMAGE_BLOCK -> component.setComponentData(compReq.getSrc());
                    case HEADING_TAG -> component.setComponentData(compReq.getHeadingText());
                    case TABLE_BLOCK -> component.setComponentData(compReq.getTableRows());
                }


                component.setParsedData(null); // optional
                components.add(component);
            }
        }

        post.setComponents(components);

        return postRepository.save(post);
    }

    @Override
    public PostComponent createComponent(PostEntity post, ComponentsRequest componentReq, int order) {
        PostComponent component = new PostComponent();
        component.setComponentType(componentReq.getType().toString());
        component.setComponentData(componentReq.getContent()); // or whichever field is relevant
        component.setComponentOrder(order);
        component.setParsedData(null); // set parsedData if needed

        if (post.getComponents() != null) {
            post.getComponents().add(component);
        } else {
            post.setComponents(new ArrayList<>());
            post.getComponents().add(component);
        }

        postRepository.save(post);
        return component;
    }
}