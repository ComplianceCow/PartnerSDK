package com.org.continube.metricstream.models.partner.run;

public class RunResponse {
    private String runID;
    private String controlGroupRef;
    private String customerAppRef;
    private String fromDate;
    private String toDate;
    private RunStatus runStatus;
    public String getRunID() {
        return runID;
    }
    public void setRunID(String runID) {
        this.runID = runID;
    }
    public String getControlGroupRef() {
        return controlGroupRef;
    }
    public void setControlGroupRef(String controlGroupRef) {
        this.controlGroupRef = controlGroupRef;
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
    public RunStatus getRunStatus() {
        return runStatus;
    }
    public void setRunStatus(RunStatus runStatus) {
        this.runStatus = runStatus;
    }
}
