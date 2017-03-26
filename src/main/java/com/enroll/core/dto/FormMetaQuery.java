package com.enroll.core.dto;

import org.apache.commons.lang3.StringUtils;

import com.enroll.core.search.CommonQuery;
import com.enroll.core.search.Search;
import com.enroll.core.search.SearchField;

public class FormMetaQuery extends CommonQuery {

	private SearchField formId;

	private SearchField formName;

	private SearchField status;
	
	private SearchField createdDate;

	public SearchField getFormId() {
		return formId;
	}

	public void setFormId(SearchField formId) {
		this.formId = formId;
	}

	public SearchField getFormName() {
		return formName;
	}

	public void setFormName(SearchField formName) {
		this.formName = formName;
	}

	public long getSearchFormId() {
		if (formId != null && formId.getSearch() != null) {
			return Long.valueOf(formId.getSearch().getValue());
		}
		return 0;
	}

	public SearchField getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(SearchField createdDate) {
		this.createdDate = createdDate;
	}

	public String getSearchFormName() {
		if (formName != null && formName.getSearch() != null) {
			return formName.getSearch().getValue();
		}
		return StringUtils.EMPTY;
	}

	public String getSearchFormStatus() {
		if (status != null && status.getSearch() != null) {
			return status.getSearch().getValue();
		}
		return StringUtils.EMPTY;
	}

	public void setSearchFormStatus(String status) {
		this.status = new SearchField(new Search(status));
	}
}