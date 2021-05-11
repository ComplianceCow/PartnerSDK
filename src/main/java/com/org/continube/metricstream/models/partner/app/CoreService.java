package com.org.continube.metricstream.models.partner.app;

import java.util.List;

import com.org.continube.metricstream.models.partner.controlgroup.NameValueProperty;

public class CoreService {
    private CoreServiceType type;
    private List<NameValueProperty> attributes;
    public CoreServiceType getType() {
        return type;
    }
    public void setType(CoreServiceType type) {
        this.type = type;
    }
    public List<NameValueProperty> getAttributes() {
        return attributes;
    }
    public void setAttributes(List<NameValueProperty> attributes) {
        this.attributes = attributes;
    }
}
