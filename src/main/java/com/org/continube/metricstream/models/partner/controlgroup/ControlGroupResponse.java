package com.org.continube.metricstream.models.partner.controlgroup;

public class ControlGroupResponse {
    private String requestID;
    private ControlGroupStatus controlGroupStatus;
    public String getRequestID() {
        return requestID;
    }
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
    public ControlGroupStatus getControlGroupStatus() {
        return controlGroupStatus;
    }
    public void setControlGroupStatus(ControlGroupStatus controlGroupStatus) {
        this.controlGroupStatus = controlGroupStatus;
    }
}
