package com.enroll.rest.dto;

import java.io.Serializable;

public class RestFieldValue implements Serializable {

	private static final long serialVersionUID = 6354724255148804197L;

	private String name;
	private String value;	

	public RestFieldValue() {
	}

	public RestFieldValue(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
