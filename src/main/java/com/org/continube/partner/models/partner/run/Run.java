package com.org.continube.partner.models.partner.run;

import java.util.List;

public class Run {
    private String controlGroupRef;
    private String runID;
    private String customerRef;
    private String customerAppRef;
    private String fromDate;
    private String toDate;
    private List<ControlInput> controlInputs;
    private RunStatus runStatus;
    private List<ControlOutput> controlOutputs;
    public String getControlGroupRef() {
        return controlGroupRef;
    }
    public void setControlGroupRef(String controlGroupRef) {
        this.controlGroupRef = controlGroupRef;
    }
    public String getRunID() {
        return runID;
    }
    public void setRunID(String runID) {
        this.runID = runID;
    }
    public String getCustomerRef() {
        return customerRef;
    }
    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }
    public String getCustomerAppRef() {
        return customerAppRef;
    }
    public void setCustomerAppRef(String customerAppRef) {
        this.customerAppRef = customerAppRef;
    }
    public String getFromDate() {
        return fromDate;
    }
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    public String getToDate() {
        return toDate;
    }
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    public List<ControlInput> getControlInputs() {
        return controlInputs;
    }
    public void setControlInputs(List<ControlInput> controlInputs) {
        this.controlInputs = controlInputs;
    }
    public RunStatus getRunStatus() {
        return runStatus;
    }
    public void setRunStatus(RunStatus runStatus) {
        this.runStatus = runStatus;
    }
    public List<ControlOutput> getControlOutputs() {
        return controlOutputs;
    }
    public void setControlOutputs(List<ControlOutput> controlOutputs) {
        this.controlOutputs = controlOutputs;
    }
}
