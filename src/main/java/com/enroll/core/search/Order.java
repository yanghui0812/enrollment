package com.enroll.core.search;

public class Order {

	private int column;
	private String dir;
	private SearchField field;

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public SearchField getField() {
		return field;
	}

	public void setField(SearchField field) {
		this.field = field;
	}
}
