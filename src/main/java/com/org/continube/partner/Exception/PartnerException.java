package com.org.continube.partner.Exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PartnerException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private final Map<String, List<String>> errors;

	public PartnerException(String msg) {
		super(msg);
		errors = new LinkedHashMap<>();
    }
    
    public PartnerException(Exception e) {
		super(e);
		errors = new LinkedHashMap<>();
	}

	public PartnerException(String msg, Exception e) {
		super(msg,e);
		errors = new LinkedHashMap<>();
	}

	public PartnerException(String msg, Map<String, List<String>> errors) {
		super(msg);
		this.errors = errors;
	}

	public Map<String, List<String>> getErrors() {
		return errors;
	}
}
