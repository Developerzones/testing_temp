package com.example.demo.framework.post.model;

import java.util.Map;

public class PostComponentDocument {

    private ComponentType componentType;
    private Integer componentOrder;
    private String componentData;

    // ✅ Parsed data for view rendering (NOT stored in Mongo)
    private transient Map<String, Object> parsedData;

    public PostComponentDocument() {}

    public PostComponentDocument(
            ComponentType componentType,
            Integer componentOrder,
            String componentData
    ) {
        this.componentType = componentType;
        this.componentOrder = componentOrder;
        this.componentData = componentData;
    }

    // -------- GETTERS & SETTERS --------

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public Integer getComponentOrder() {
        return componentOrder;
    }

    public void setComponentOrder(Integer componentOrder) {
        this.componentOrder = componentOrder;
    }

    public String getComponentData() {
        return componentData;
    }

    public void setComponentData(String componentData) {
        this.componentData = componentData;
    }

    public Map<String, Object> getParsedData() {
        return parsedData;
    }

    public void setParsedData(Map<String, Object> parsedData) {
        this.parsedData = parsedData;
    }
}
