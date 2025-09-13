package com.example.demo.framework.post.model;


import jakarta.persistence.*;
import java.util.Map;

@Entity
@Table(name = "post_components")
public class PostComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity posts;

    @Enumerated(EnumType.STRING)
    @Column(name = "component_type", nullable = false)
    private ComponentType componentType;

    @Column(name = "component_order", nullable = false)
    private Integer componentOrder;

    @Column(name = "component_data", columnDefinition = "TEXT")
    private String componentData;

    // Transient field for parsed data (not stored in database)
    @Transient
    private Map<String, Object> parsedData;

    // Constructors
    public PostComponent() {}

    public PostComponent(PostEntity posts, ComponentType componentType, Integer componentOrder, String componentData) {
        this.posts = posts;
        this.componentType = componentType;
        this.componentOrder = componentOrder;
        this.componentData = componentData;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PostEntity getPost() { return posts; }
    public void setPost(PostEntity post) { this.posts = post; }

    public ComponentType getComponentType() { return componentType; }
    public void setComponentType(ComponentType componentType) { this.componentType = componentType; }

    public Integer getComponentOrder() { return componentOrder; }
    public void setComponentOrder(Integer componentOrder) { this.componentOrder = componentOrder; }

    public String getComponentData() { return componentData; }
    public void setComponentData(String componentData) { this.componentData = componentData; }

    public Map<String, Object> getParsedData() { return parsedData; }
    public void setParsedData(Map<String, Object> parsedData) { this.parsedData = parsedData; }
}
