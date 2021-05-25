package com.org.continube.partner.models.partner.plan;

import java.util.List;

public class Plan {
    private String planRef;
    private PlanStatus  planStatus;
    private List<ControlSpec> controlSpecs;
    
    public String getPlanRef() {
        return planRef;
    }
    public void setPlanRef(String planRef) {
        this.planRef = planRef;
    }
    public PlanStatus getPlanStatus() {
        return planStatus;
    }
    public void setPlanStatus(PlanStatus planStatus) {
        this.planStatus = planStatus;
    }
    public List<ControlSpec> getControlSpecs() {
        return controlSpecs;
    }
    public void setControlSpecs(List<ControlSpec> controlSpecs) {
        this.controlSpecs = controlSpecs;
    }   
}
