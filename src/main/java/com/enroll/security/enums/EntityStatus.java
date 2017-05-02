package com.enroll.security.enums;

import java.util.Arrays;
import java.util.Optional;

public enum EntityStatus {
	
	ACIVE("Y", "Acive"), INACTIVE("N", "Inactive");
	private String key;
	private String desc;

	private EntityStatus(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}
	
	public static boolean isAcive(String key) {
		return ACIVE.getKey().equalsIgnoreCase(key);
	}
	
	public static boolean isInactive(String key) {
		return INACTIVE.getKey().equalsIgnoreCase(key);
	}
	
	public static EntityStatus getEntityStatus(String key) {
		Optional<EntityStatus> optional = Arrays.stream(values()).filter(type -> type.getKey().equalsIgnoreCase(key)).findFirst();
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new RuntimeException("Fail to find entity status");
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
