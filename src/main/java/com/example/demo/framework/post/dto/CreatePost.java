package com.example.demo.framework.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePost {
    @NotBlank(message = "Post title is required")
    private String headingText;

    @NotBlank(message = "Author name is required")
    private String authorName;

   @NotBlank(message = "Slug is required")
    private String slug;
    
    private String category;

    private List<ComponentsRequest> components;

}
