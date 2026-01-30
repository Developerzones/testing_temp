package com.example.demo.framework.post.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class PostComponent {

    @Id
    private String id;

    private String componentType;
    private int componentOrder;
    private String componentData;
    private Object parsedData; // optional

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public int getComponentOrder() {
        return componentOrder;
    }

    public void setComponentOrder(int componentOrder) {
        this.componentOrder = componentOrder;
    }

    public String getComponentData() {
        return componentData;
    }

    public void setComponentData(String componentData) {
        this.componentData = componentData;
    }

    public Object getParsedData() {
        return parsedData;
    }

    public void setParsedData(Object parsedData) {
        this.parsedData = parsedData;
    }
// getters and setters
}
