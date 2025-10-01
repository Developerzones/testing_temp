package com.example.demo.framework.post.controller;



import com.example.demo.framework.post.dto.CreatePost;
import com.example.demo.framework.post.model.PostEntity;
import com.example.demo.framework.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("post")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    // GET /posts - Get all posts
    @GetMapping("/posts")
    public ResponseEntity<Object> getAllPosts() {
        try {
            List<PostEntity> posts = postService.getAllPosts();

            List<Map<String, Object>> safePosts = new ArrayList<>();
            for (PostEntity post : posts) {
                Map<String, Object> safePost = new HashMap<>();
                safePost.put("id", post.getId());
                safePost.put("headingText", post.getHeadingText());
                safePost.put("authorName", post.getAuthorName());
                safePost.put("category", post.getCategory());
                safePost.put("componentCount", post.getComponents().size());

                if (post.getCreatedAt() != null) {
                    safePost.put("createdAt", post.getCreatedAt().toString());
                }

                safePosts.add(safePost);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", safePosts.size());
            response.put("data", safePosts);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // GET /post/{id} - Get specific post with components
    @GetMapping("/post/{slug}")
    public ResponseEntity<Object> getPostBySlug(@PathVariable String Slug) {
        try {
            Optional<PostEntity> postOpt = postService.getPostBySlug(Slug);
            if (postOpt.isPresent()) {
                PostEntity post = postOpt.get();

                Map<String, Object> safePost = new HashMap<>();
                safePost.put("id", post.getId());
                safePost.put("headingText", post.getHeadingText());
                safePost.put("authorName", post.getAuthorName());
                safePost.put("category", post.getCategory());

                if (post.getAuthorDate() != null) {
                    safePost.put("authorDate", post.getAuthorDate().toString());
                }
                if (post.getCreatedAt() != null) {
                    safePost.put("createdAt", post.getCreatedAt().toString());
                }
                if (post.getUpdatedAt() != null) {
                    safePost.put("updatedAt", post.getUpdatedAt().toString());
                }

                // Add components
                List<Map<String, Object>> safeComponents = new ArrayList<>();
                for (var component : post.getComponents()) {
                    Map<String, Object> safeComponent = new HashMap<>();
                    safeComponent.put("id", component.getId());
                    safeComponent.put("componentType", component.getComponentType().toString());
                    safeComponent.put("componentOrder", component.getComponentOrder());
                    safeComponent.put("componentData", component.getComponentData());

                    if (component.getParsedData() != null) {
                        safeComponent.put("parsedData", component.getParsedData());
                    }

                    safeComponents.add(safeComponent);
                }

                safePost.put("components", safeComponents);
                safePost.put("componentCount", safeComponents.size());

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("data", safePost);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Post not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // POST /post/create - Create new post
    @PostMapping("/post/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody CreatePost request) {
        try {

            PostEntity createdPost = postService.createPost(request);

            Map<String, Object> safePost = new HashMap<>();
            System.out.println("Before id");
            safePost.put("id", createdPost.getId());
            safePost.put("headingText", createdPost.getHeadingText());
            safePost.put("authorName", createdPost.getAuthorName());
            safePost.put("category", createdPost.getCategory());
            safePost.put("componentCount", createdPost.getComponents().size());
            safePost.put("slug", createdPost.getSlug());

            System.out.println("afetr id");

            if (createdPost.getCreatedAt() != null) {
                safePost.put("createdAt", createdPost.getCreatedAt().toString());
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Post created successfully");
            response.put("data", safePost);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
