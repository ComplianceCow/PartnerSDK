package com.org.continube.partner.models.partner.controlgroup;

public class CallbackInfo {
    private String url;
    private Credential credential;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Credential getCredential() {
        return credential;
    }
    public void setCredential(Credential credential) {
        this.credential = credential;
    }
}
