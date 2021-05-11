package com.org.continube.metricstream.models.partner.plan;

public class PlanBasicInfo {

    private String planRef;
    private PlanStatus  planStatus;

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
}
