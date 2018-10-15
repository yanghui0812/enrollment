package com.enroll.core.search;

public class Search {

	private String value;
	private String regex;

	public Search() {
	}

	public Search(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
}
