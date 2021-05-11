package com.org.continube.metricstream.models.partner.run;

import java.util.List;

public class InstanceOutput {
    private String instanceName;
    private InstanceExecutionStatus instanceExecutionStatus;
    private List<OutputFileInfo> outputFilesInfo;
    private List<OutputFileInfo> files;
    private String errorDescription;
    public String getInstanceName() {
        return instanceName;
    }
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
    public InstanceExecutionStatus getInstanceExecutionStatus() {
        return instanceExecutionStatus;
    }
    public void setInstanceExecutionStatus(InstanceExecutionStatus instanceExecutionStatus) {
        this.instanceExecutionStatus = instanceExecutionStatus;
    }
    public List<OutputFileInfo> getOutputFilesInfo() {
        return outputFilesInfo;
    }
    public void setOutputFilesInfo(List<OutputFileInfo> outputFilesInfo) {
        this.outputFilesInfo = outputFilesInfo;
    }
    public List<OutputFileInfo> getFiles() {
        return files;
    }
    public void setFiles(List<OutputFileInfo> files) {
        this.files = files;
    }
    public String getErrorDescription() {
        return errorDescription;
    }
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }   
}
