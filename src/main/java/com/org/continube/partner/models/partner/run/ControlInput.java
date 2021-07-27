package com.org.continube.partner.models.partner.run;

import java.util.List;

import com.org.continube.partner.models.partner.controlgroup.NameValueProperty;

public class ControlInput {
    private String controlNumber;
    private List<NameValueProperty> inputValues;
    public String getControlNumber() {
        return controlNumber;
    }
    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }
    public List<NameValueProperty> getInputValues() {
        return inputValues;
    }
    public void setInputValues(List<NameValueProperty> inputValues) {
        this.inputValues = inputValues;
    }
}
