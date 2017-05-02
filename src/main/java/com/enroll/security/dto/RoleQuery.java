package com.enroll.security.dto;

import com.enroll.core.search.CommonQuery;
import com.enroll.core.search.SearchField;

public class RoleQuery extends CommonQuery {

	private SearchField name;

	private SearchField status;

	public SearchField getName() {
		return name;
	}

	public void setName(SearchField name) {
		this.name = name;
	}

	public SearchField getStatus() {
		return status;
	}

	public void setStatus(SearchField status) {
		this.status = status;
	}
}