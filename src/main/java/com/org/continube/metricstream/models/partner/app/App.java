package com.org.continube.metricstream.models.partner.app;

import java.util.List;

import com.org.continube.metricstream.models.partner.controlgroup.NameValueProperty;

public class App {
    private String customerRef;
    private String customerAppRef;
    private CoreService coreService;
    private List<OtherService> otherServices;
    private List<NameValueProperty> tags;
    private AppStatus appStatus;
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
    public CoreService getCoreService() {
        return coreService;
    }
    public void setCoreService(CoreService coreService) {
        this.coreService = coreService;
    }
    public List<OtherService> getOtherServices() {
        return otherServices;
    }
    public void setOtherServices(List<OtherService> otherServices) {
        this.otherServices = otherServices;
    }
    public List<NameValueProperty> getTags() {
        return tags;
    }
    public void setTags(List<NameValueProperty> tags) {
        this.tags = tags;
    }
    public AppStatus getAppStatus() {
        return appStatus;
    }
    public void setAppStatus(AppStatus appStatus) {
        this.appStatus = appStatus;
    }
}
