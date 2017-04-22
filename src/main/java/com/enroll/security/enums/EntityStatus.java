package com.enroll.security.enums;

public enum EntityStatus {
	
	ACIVE("Y", "Acive"), INACTIVE("N", "inactive");
	private String key;
	private String desc;

	private EntityStatus(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}
	
	public boolean isAcive(String key) {
		return ACIVE.getKey().equalsIgnoreCase(key);
	}
	
	public boolean isInactive(String key) {
		return INACTIVE.getKey().equalsIgnoreCase(key);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
