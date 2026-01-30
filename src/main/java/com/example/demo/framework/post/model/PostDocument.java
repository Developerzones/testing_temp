package com.example.demo.framework.post.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "posts")
public class PostDocument {

    @Id
    private String id; // Mongo uses String/ObjectId

    private String headingText;
    private String authorName;
    private String category;
    private String slug;

    private LocalDateTime authorDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<PostComponentDocument> components = new ArrayList<>();

    public PostDocument() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadingText() {
        return headingText;
    }

    public void setHeadingText(String headingText) {
        this.headingText = headingText;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public LocalDateTime getAuthorDate() {
        return authorDate;
    }

    public void setAuthorDate(LocalDateTime authorDate) {
        this.authorDate = authorDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<PostComponentDocument> getComponents() {
        return components;
    }

    public void setComponents(List<PostComponentDocument> components) {
        this.components = components;
    }


    // getters & setters
}
