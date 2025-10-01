package com.example.demo.framework.post.service;

import com.example.demo.framework.post.dto.ComponentsRequest;
import com.example.demo.framework.post.dto.CreatePost;
import com.example.demo.framework.post.model.ComponentType;
import com.example.demo.framework.post.model.PostComponent;
import com.example.demo.framework.post.model.PostEntity;
import com.example.demo.framework.post.repo.PostRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepo repo;

    @Autowired
    private ObjectMapper objectMapper;



    // Get all posts
    public List<PostEntity> getAllPosts() {
        return repo.findAllByOrderByCreatedAtDesc();
    }


    // Get post by ID
 
    public Optional<PostEntity> getPostBySlug(String slug) {
    return repo.findBySlug(slug);
}


    // Get post with parsed components for view display
    public PostEntity getPostWithParsedComponents(Long id) {
        Optional<PostEntity> postOptional = repo.findByIdWithComponents(id);
        if (postOptional.isEmpty()) {
            throw new RuntimeException("Post not found");
        }

        PostEntity post = postOptional.get();

        // Parse component data for easier template access
        for (PostComponent component : post.getComponents()) {
            try {
                Map<String, Object> data = objectMapper.readValue(component.getComponentData(), Map.class);
                component.setParsedData(data);

                // Debug logging for image components
                if (component.getComponentType() == ComponentType.IMAGE_BLOCK) {
                    System.out.println("Image component data: " + component.getComponentData());
                    System.out.println("Parsed src: " + data.get("src"));
                    System.out.println("Parsed alt: " + data.get("alt"));
                }
            } catch (JsonProcessingException e) {
                System.err.println("Error parsing component data: " + e.getMessage());
                Map<String, Object> fallbackData = new HashMap<>();
                fallbackData.put("error", "Could not parse component data");
                component.setParsedData(fallbackData);
            }
        }

        return post;
    }




    // Create new post
    @Transactional
    public PostEntity createPost(CreatePost request) {
        System.out.println("Before create");

        PostEntity post = new PostEntity();
        post.setHeadingText(request.getHeadingText());
        post.setAuthorName(request.getAuthorName());
        post.setCategory(request.getCategory());
        post.setAuthorDate(LocalDateTime.now());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

    // set slug from request
    post.setSlug(request.getSlug());
        // Save post first to get ID
        post = repo.save(post);

        // Add components if provided
        if (request.getComponents() != null && !request.getComponents().isEmpty()) {
            List<PostComponent> components = new ArrayList<>();
            int order = 1;
            for (ComponentsRequest componentReq : request.getComponents()) {
                if (componentReq.getType() != null) { // Only add components with valid type
                    PostComponent component = createComponent(post, componentReq, order++);
                    System.out.println("Before addning");

                    components.add(component);
                }
            }
            post.setComponents(components);
        }

        return repo.save(post);
    }



    // Helper method to create new component
    public PostComponent createComponent(PostEntity post, ComponentsRequest componentReq, int order) {
        try {
            String componentData;

            switch (componentReq.getType()) {
                case POST_BODY:
                    Map<String, String> textData = new HashMap<>();
                    textData.put("content", componentReq.getContent());
                    componentData = objectMapper.writeValueAsString(textData);
                    break;

                case CODE_BLOCK_WITH_COPY:
                    Map<String, String> codeData = new HashMap<>();
                    codeData.put("code", componentReq.getCode());
                    componentData = objectMapper.writeValueAsString(codeData);
                    break;

                case IMAGE_BLOCK:
                    Map<String, String> imageData = new HashMap<>();
                    imageData.put("src", componentReq.getSrc());
                    imageData.put("alt", componentReq.getAlt());
                    componentData = objectMapper.writeValueAsString(imageData);
                    break;

                case HEADING_TAG:
                    Map<String, String> headingData = new HashMap<>();
                    headingData.put("as", componentReq.getHeadingLevel());
                    headingData.put("text", componentReq.getHeadingText());
                    componentData = objectMapper.writeValueAsString(headingData);
                    break;

                case TABLE_BLOCK:
                    Map<String, Object> tableData = new HashMap<>();
                    try {
                        List<String> headers = objectMapper.readValue(componentReq.getTableHeaders(), List.class);
                        List<List<String>> rows = objectMapper.readValue(componentReq.getTableRows(), List.class);
                        tableData.put("headers", headers);
                        tableData.put("rows", rows);
                    } catch (JsonProcessingException e) {
                        // Fallback for malformed JSON
                        tableData.put("headers", Arrays.asList("Header"));
                        tableData.put("rows", Arrays.asList(Arrays.asList("Data")));
                    }
                    componentData = objectMapper.writeValueAsString(tableData);
                    break;

                default:
                    componentData = "{}";
            }

            return new PostComponent(post, componentReq.getType(), order, componentData);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing component data", e);
        }
    }


}
