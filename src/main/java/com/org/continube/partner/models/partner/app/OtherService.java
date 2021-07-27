package com.org.continube.partner.models.partner.app;

import com.org.continube.partner.models.partner.controlgroup.Credential;

public class OtherService {
    private String name;
    private OtherServiceType type;
    private String url;
    private String port;
    private Credential credential;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public OtherServiceType getType() {
        return type;
    }
    public void setType(OtherServiceType type) {
        this.type = type;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public Credential getCredential() {
        return credential;
    }
    public void setCredential(Credential credential) {
        this.credential = credential;
    }
}
