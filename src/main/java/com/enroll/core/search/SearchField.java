package com.enroll.core.search;

public class SearchField {

	private String data;
	private String name;
	private String searchable;
	private String orderable;
	private Search search;

	public SearchField() {
	}

	public SearchField(Search search) {
		this.search = search;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSearchable() {
		return searchable;
	}

	public void setSearchable(String searchable) {
		this.searchable = searchable;
	}

	public String getOrderable() {
		return orderable;
	}

	public void setOrderable(String orderable) {
		this.orderable = orderable;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	@Override
	public String toString() {
		return "SearchField [data=" + data + ", name=" + name + ", searchable=" + searchable + ", orderable="
				+ orderable + ", search=" + search + "]";
	}
}