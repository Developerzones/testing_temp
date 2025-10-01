package com.example.demo.framework.post.dto;


import com.example.demo.framework.post.model.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentsRequest {

    private ComponentType type;

    // Text content fields
    private String content;

    // Code block fields
    private String code;

    // Image fields
    private String src;
    private String alt;

    // Heading fields
    private String headingLevel;
    private String headingText;

    // Table fields
    private String tableHeaders;
    private String tableRows;

}
