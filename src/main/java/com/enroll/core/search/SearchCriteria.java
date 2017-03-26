
package com.enroll.core.search;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class SearchCriteria {

	private int page = 1;
	private int start = 1;
	private int pageSize = 10;;
	private Search search;
	private int draw;
	private String type;
	private List<SearchOrder> order;
	private List<SearchField> columns;

	public SearchCriteria() {
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setLength(int length) {
		pageSize = length;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<SearchField> getColumns() {
		return columns;
	}

	public void setColumns(List<SearchField> columns) {
		this.columns = columns;
	}

	public void searchMapping(Object obj) {
		BeanUtils.copyProperties(this, obj);
		for (SearchOrder order : order) {
			order.setField(columns.get(order.getColumn()));
		}
		for (SearchField field : columns) {
			setObjectPropertyValue(field.getName(), obj, field);
		}
	}

	private void setObjectPropertyValue(String name, Object obj, Object value) {
		try {
			PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(obj.getClass(), name);
			if (pd == null) {
				return;
			}
			Method method = BeanUtils.getWriteMethodParameter(pd).getMethod();
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
}