package com.enroll.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchResult<T> implements Serializable {
	
	private static final long serialVersionUID = -3807343846302654116L;
	private List<T> data = new ArrayList<T>();
	private Integer recordsTotal;
	private Integer recordsFiltered;
	private Integer draw;

	public void addElement(T t) {
		data.add(t);
	}

	public List<T> getData() {
		return data;
	}

	public void addAll(List<T> data) {
		this.data.addAll(data);
	}

	public void setResults(List<T> data) {
		this.addAll(data);
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}
}