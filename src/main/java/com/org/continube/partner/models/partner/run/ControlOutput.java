package com.org.continube.partner.models.partner.run;

import java.util.List;

public class ControlOutput {
    private String controlNumber;
    private ControlExecutionStatus controlExecutionStatus;
    private ComplianceStatus complianceStatus;
    private double compliancePCT;
    private List<InstanceOutput> instanceOutputs;
    public String getControlNumber() {
        return controlNumber;
    }
    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }
    public ControlExecutionStatus getControlExecutionStatus() {
        return controlExecutionStatus;
    }
    public void setControlExecutionStatus(ControlExecutionStatus controlExecutionStatus) {
        this.controlExecutionStatus = controlExecutionStatus;
    }
    public ComplianceStatus getComplianceStatus() {
        return complianceStatus;
    }
    public void setComplianceStatus(ComplianceStatus complianceStatus) {
        this.complianceStatus = complianceStatus;
    }
    public double getCompliancePCT() {
        return compliancePCT;
    }
    public void setCompliancePCT(double compliancePCT) {
        this.compliancePCT = compliancePCT;
    }
    public List<InstanceOutput> getInstanceOutputs() {
        return instanceOutputs;
    }
    public void setInstanceOutputs(List<InstanceOutput> instanceOutputs) {
        this.instanceOutputs = instanceOutputs;
    }
}
