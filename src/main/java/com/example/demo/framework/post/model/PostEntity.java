package com.example.demo.framework.post.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "heading_text", nullable = false)
    private String headingText;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "category")
    private String category;

    @Column(name = "author_date")
    private LocalDateTime authorDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("componentOrder ASC")
    private List<PostComponent> components = new ArrayList<>();

    // Constructors
    public PostEntity() {}

    public PostEntity(String headingText, String authorName, String category, LocalDateTime authorDate) {
        this.headingText = headingText;
        this.authorName = authorName;
        this.category = category;
        this.authorDate = authorDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHeadingText() { return headingText; }
    public void setHeadingText(String headingText) { this.headingText = headingText; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDateTime getAuthorDate() { return authorDate; }
    public void setAuthorDate(LocalDateTime authorDate) { this.authorDate = authorDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<PostComponent> getComponents() { return components; }
    public void setComponents(List<PostComponent> components) { this.components = components; }
}
