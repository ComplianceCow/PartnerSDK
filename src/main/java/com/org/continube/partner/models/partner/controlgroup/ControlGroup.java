package com.org.continube.partner.models.partner.controlgroup;

import java.util.List;

public class ControlGroup {
    private String controlGroupRef;
    private String planRef;
    private String customerRef;
    private String customerSecurityToken;
    private List<String> controlNumbers;
    private CallbackInfo runOutputCallback;
    private ControlGroupStatus controlGroupStatus;
    public String getControlGroupRef() {
        return controlGroupRef;
    }
    public void setControlGroupRef(String controlGroupRef) {
        this.controlGroupRef = controlGroupRef;
    }
    public String getPlanRef() {
        return planRef;
    }
    public void setPlanRef(String planRef) {
        this.planRef = planRef;
    }
    public String getCustomerRef() {
        return customerRef;
    }
    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }
    public String getCustomerSecurityToken() {
        return customerSecurityToken;
    }
    public void setCustomerSecurityToken(String customerSecurityToken) {
        this.customerSecurityToken = customerSecurityToken;
    }
    public List<String> getControlNumbers() {
        return controlNumbers;
    }
    public void setControlNumbers(List<String> controlNumbers) {
        this.controlNumbers = controlNumbers;
    }
    public CallbackInfo getRunOutputCallback() {
        return runOutputCallback;
    }
    public void setRunOutputCallback(CallbackInfo runOutputCallback) {
        this.runOutputCallback = runOutputCallback;
    }
    public ControlGroupStatus getControlGroupStatus() {
        return controlGroupStatus;
    }
    public void setControlGroupStatus(ControlGroupStatus controlGroupStatus) {
        this.controlGroupStatus = controlGroupStatus;
    }
}
