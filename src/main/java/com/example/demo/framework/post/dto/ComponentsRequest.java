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



    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getHeadingLevel() {
        return headingLevel;
    }

    public void setHeadingLevel(String headingLevel) {
        this.headingLevel = headingLevel;
    }

    public String getHeadingText() {
        return headingText;
    }

    public void setHeadingText(String headingText) {
        this.headingText = headingText;
    }

    public String getTableHeaders() {
        return tableHeaders;
    }

    public void setTableHeaders(String tableHeaders) {
        this.tableHeaders = tableHeaders;
    }

    public String getTableRows() {
        return tableRows;
    }

    public void setTableRows(String tableRows) {
        this.tableRows = tableRows;
    }
}
