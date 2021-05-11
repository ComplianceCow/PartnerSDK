package com.org.continube.metricstream.models.partner.app;

import java.util.List;

public class AppResponse {
    private String customerAppRef;
    private AppStatus appStatus;
    private List<Error> errors;
    public String getCustomerAppRef() {
        return customerAppRef;
    }
    public void setCustomerAppRef(String customerAppRef) {
        this.customerAppRef = customerAppRef;
    }
    public AppStatus getAppStatus() {
        return appStatus;
    }
    public void setAppStatus(AppStatus appStatus) {
        this.appStatus = appStatus;
    }
    public List<Error> getErrors() {
        return errors;
    }
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
