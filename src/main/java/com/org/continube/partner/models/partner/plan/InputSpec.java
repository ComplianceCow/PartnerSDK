package com.org.continube.partner.models.partner.plan;

public class InputSpec {
    private String name;
    private InputType type;
    private String description;
    private String templateID;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public InputType getType() {
        return type;
    }
    public void setType(InputType type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTemplateID() {
        return templateID;
    }
    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }
}
