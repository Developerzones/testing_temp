package com.example.demo.framework.post.controller;

import com.example.demo.framework.post.dto.CreatePost;
import com.example.demo.framework.post.model.PostComponent;
import com.example.demo.framework.post.model.PostEntity;
import com.example.demo.framework.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("post")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    // GET /post/posts - Get all posts
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
                safePost.put("componentCount", post.getComponents() != null ? post.getComponents().size() : 0);
                safePost.put("createdAt", post.getCreatedAt() != null ? post.getCreatedAt().toString() : null);
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

    // GET /post/post/{slug} - Get specific post by slug with components
    @GetMapping("/post/{slug}")
    public ResponseEntity<Object> getPostBySlug(@PathVariable String slug) {
        try {
            Optional<PostEntity> postOpt = postService.getPostBySlug(slug);
            if (postOpt.isPresent()) {
                PostEntity post = postOpt.get();

                Map<String, Object> safePost = new HashMap<>();
                safePost.put("id", post.getId());
                safePost.put("headingText", post.getHeadingText());
                safePost.put("authorName", post.getAuthorName());
                safePost.put("category", post.getCategory());
                safePost.put("slug", post.getSlug());
                safePost.put("authorDate", post.getAuthorDate() != null ? post.getAuthorDate().toString() : null);
                safePost.put("createdAt", post.getCreatedAt() != null ? post.getCreatedAt().toString() : null);
                safePost.put("updatedAt", post.getUpdatedAt() != null ? post.getUpdatedAt().toString() : null);

                // Add components if any
                List<Map<String, Object>> safeComponents = new ArrayList<>();
                if (post.getComponents() != null) {
                    for (PostComponent component : post.getComponents()) {
                        Map<String, Object> safeComponent = new HashMap<>();
                        safeComponent.put("id", component.getId());
                        safeComponent.put("componentType", component.getComponentType());
                        safeComponent.put("componentOrder", component.getComponentOrder());
                        safeComponent.put("componentData", component.getComponentData());
                        safeComponent.put("parsedData", component.getParsedData());
                        safeComponents.add(safeComponent);
                    }
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

    // POST /post/post/create - Create a new post
    @PostMapping("/post/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody CreatePost request) {
        try {
            PostEntity createdPost = postService.createPost(request);

            Map<String, Object> safePost = new HashMap<>();
            safePost.put("id", createdPost.getId());
            safePost.put("headingText", createdPost.getHeadingText());
            safePost.put("authorName", createdPost.getAuthorName());
            safePost.put("category", createdPost.getCategory());
            safePost.put("slug", createdPost.getSlug());
            safePost.put("componentCount", createdPost.getComponents() != null ? createdPost.getComponents().size() : 0);
            safePost.put("createdAt", createdPost.getCreatedAt() != null ? createdPost.getCreatedAt().toString() : null);

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
