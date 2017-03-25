package com.enroll.core.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;

import com.enroll.common.AppConstant;
import com.enroll.common.DateUtils;
import com.enroll.core.search.CommonQuery;
import com.enroll.core.search.Search;
import com.enroll.core.search.SearchField;

public class EnrollmentQuery extends CommonQuery {

	private SearchField registerId;

	private SearchField status;

	private SearchField phoneNumber;

	private SearchField id;

	private SearchField formId;

	private SearchField registerDate;

	private LocalDateTime begin;

	private LocalDateTime end;

	public EnrollmentQuery() {
		super();
	}

	public EnrollmentQuery(int pageSize, int start) {
		this.pageSize = pageSize;
		this.start = start;
	}

	public SearchField getRegisterId() {
		return registerId;
	}

	public void setRegisterId(SearchField registerId) {
		this.registerId = registerId;
	}

	public SearchField getStatus() {
		return status;
	}

	public void setStatus(SearchField status) {
		this.status = status;
	}

	public SearchField getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(SearchField phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public SearchField getId() {
		return id;
	}

	public void setId(SearchField id) {
		this.id = id;
	}

	public SearchField getFormId() {
		return formId;
	}

	public void setFormId(SearchField formId) {
		this.formId = formId;
	}

	public SearchField getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(SearchField registerDate) {
		this.registerDate = registerDate;
		if (registerDate.getSearch() == null || StringUtils.isBlank(registerDate.getSearch().getValue())) {
			return;
		}
		String[] array = StringUtils.split(registerDate.getSearch().getValue(), AppConstant.CURVE);

		if (StringUtils.isNotBlank(array[0])) {
			begin = LocalDateTime.of(LocalDate.parse(array[0], DateUtils.MM_DD_YYYY), LocalTime.of(0, 0));
		}
		if (array.length > 1 && StringUtils.isNotBlank(array[1])) {
			end = LocalDateTime.of(LocalDate.parse(array[1], DateUtils.MM_DD_YYYY), LocalTime.of(0, 0));
		}
	}

	public String getSearchStatus() {
		if (status != null && status.getSearch() != null) {
			return status.getSearch().getValue();
		}
		return StringUtils.EMPTY;
	}

	public long getSearchFormId() {
		if (formId != null && StringUtils.isNotBlank(formId.getSearch().getValue())) {
			return Long.valueOf(formId.getSearch().getValue());
		}
		return 0;
	}

	public void setSearchFormId(long formId) {
		this.formId = new SearchField(new Search(String.valueOf(formId)));
	}
	
	public void setSearchRegisterDate(String registerDate) {
		setRegisterDate(new SearchField(new Search(registerDate)));
	}

	public void setSearchStatus(String status) {
		this.status = new SearchField(new Search(status));
	}

	public LocalDateTime getBegin() {
		return begin;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	@Override
	public String toString() {
		return "EnrollmentQuery [registerId=" + registerId + ", status=" + status + ", phoneNumber=" + phoneNumber
				+ ", id=" + id + ", formId=" + formId + ", registerDate=" + registerDate + ", begin=" + begin + ", end="
				+ end + ", pageSize=" + pageSize + ", start=" + start + ", draw=" + draw + ", getSearch()="
				+ getSearch() + "]";
	}
}