package com.org.continube.metricstream.models.partner.controlgroup;

import java.util.List;

public class Credential {
    private CredentialType type;
    private List<NameValueProperty> values;
    public CredentialType getType() {
        return type;
    }
    public void setType(CredentialType type) {
        this.type = type;
    }
    public List<NameValueProperty> getValues() {
        return values;
    }
    public void setValues(List<NameValueProperty> values) {
        this.values = values;
    }
}
