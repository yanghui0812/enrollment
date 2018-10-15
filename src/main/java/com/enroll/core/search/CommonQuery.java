package com.enroll.core.search;

import java.util.List;

public class CommonQuery {

	protected int pageSize;
	protected int start;
	protected int draw;
	private Search search;
	private List<SearchOrder> order;
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public List<SearchOrder> getOrder() {
		return order;
	}

	public void setOrder(List<SearchOrder> order) {
		this.order = order;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}