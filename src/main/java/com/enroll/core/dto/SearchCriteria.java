
package com.enroll.core.dto;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

public class SearchCriteria<T> {

	private Integer page = 1;
	private Integer start = 1;
	private Integer pageSize = 10;;
	private Map<String, String> search;
	private Integer draw;
	private String type;
	private T condition;

	public SearchCriteria() {
		super();
	}

	/*
	 * public SearchCriteria(ParameterMap map) { start =
	 * Integer.valueOf(map.get("start")); pageSize =
	 * Integer.valueOf(map.get("length")); draw =
	 * Integer.valueOf(map.get("draw")); search = map.get("search[value]"); type
	 * = map.get("type"); mappingSearchParameter(map, map.get("search[value]"),
	 * condition); }
	 */

	/*
	 * public SearchCriteria(Map map, T condition) { start =
	 * Integer.valueOf(map.get("start")); pageSize =
	 * Integer.valueOf(map.get("length")); draw =
	 * Integer.valueOf(map.get("draw")); search = map.get("search[value]"); type
	 * = map.get("type"); this.condition = condition;
	 * mappingSearchParameter(map, map.get("search[value]"), condition); }
	 */

	public SearchCriteria(T condition) {
		this.condition = condition;
	}

	public SearchCriteria(Integer page, Integer start, Integer pageSize) {
		super();
		this.page = page;
		this.start = start;
		this.pageSize = pageSize;
	}

	/*
	 * private void mappingSearchParameter(ParameterMap map, String search,
	 * Object obj) { if (StringUtils.isNotBlank(search)) { String decodeSearch =
	 * search; try { decodeSearch = URLDecoder.decode(search, "UTF-8"); } catch
	 * (UnsupportedEncodingException e) { e.printStackTrace(); } String[] array
	 * = StringUtils.split(decodeSearch, "&"); for (String pair : array) {
	 * String[] keyValue = pair.split("=", -1);
	 * setObjectPropertyValue(StringUtils.remove(keyValue[0],
	 * AppConstants.REDIRECT_PARAMETER_PREFIX), obj,
	 * StringUtils.trim(keyValue[1])); } } else { for (String key :
	 * map.asMap().keySet()) { setObjectPropertyValue(StringUtils.remove(key,
	 * AppConstants.REDIRECT_PARAMETER_PREFIX), obj,
	 * StringUtils.trim(map.get(key))); } } }
	 */

	private void setObjectPropertyValue(String name, Object obj, String value) {
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

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setLength(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getDraw() {
		return draw;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public T getCondition() {
		return condition;
	}

	public void setCondition(T condition) {
		this.condition = condition;
	}

	public Map<String, String> getSearch() {
		return search;
	}

	public void setSearch(Map<String, String> search) {
		this.search = search;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public String getSearchValue() {
		if (search != null) {
			return search.get("value");
		}
		return StringUtils.EMPTY;
	}
	
	public String getLikeSearchValue() {
		if (search != null) {
			return "%" + search.get("value") + "%";
		}
		return StringUtils.EMPTY;
	}

	public String getSearchRegex() {
		if (search != null) {
			return search.get("regex");
		}
		return StringUtils.EMPTY;
	}

	@Override
	public String toString() {
		return "SearchCriteria [page=" + page + ", start=" + start + ", pageSize=" + pageSize + ", search=" + search
				+ ", draw=" + draw + ", type=" + type + ", condition=" + condition + "]";
	}
}