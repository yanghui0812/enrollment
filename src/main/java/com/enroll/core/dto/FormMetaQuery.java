package com.enroll.core.dto;

import org.apache.commons.lang3.StringUtils;

import com.enroll.core.search.CommonQuery;
import com.enroll.core.search.SearchField;

public class FormMetaQuery extends CommonQuery {

	private SearchField formId;

	private SearchField formName;

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

	public String getSearchFormName() {
		if (formName != null && formName.getSearch() != null) {
			return formName.getSearch().getValue();
		}
		return StringUtils.EMPTY;
	}
}
