package com.org.continube.partner.models.partner.run;

public class OutputFileInfo {
    private String name;
    private OutputFileType type;
    private String hash;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public OutputFileType getType() {
        return type;
    }
    public void setType(OutputFileType type) {
        this.type = type;
    }
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
}
