package com.org.continube.metricstream.models.partner.plan;

import java.util.List;

public class ControlSpec {
    private String number;
    private String alias;
    private String description;
    private List<InputSpec> inputSpecs;

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<InputSpec> getInputSpecs() {
        return inputSpecs;
    }
    public void setInputSpecs(List<InputSpec> inputSpecs) {
        this.inputSpecs = inputSpecs;
    }
}
