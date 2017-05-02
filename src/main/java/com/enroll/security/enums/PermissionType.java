package com.enroll.security.enums;

import java.util.Arrays;
import java.util.Optional;

public enum PermissionType {

	MENU("m", "menu"), PAGE_OBJECT("p", "page object");

	private String key;
	private String friendlyType;

	private PermissionType(String key, String friendlyType) {
		this.key = key;
		this.friendlyType = friendlyType;
	}

	public boolean isMENUItem(String key) {
		return MENU.getKey().equalsIgnoreCase(key);
	}

	public boolean isPageObject(String key) {
		return PAGE_OBJECT.getKey().equalsIgnoreCase(key);
	}

	public static PermissionType getPermissionType(String key) {
		Optional<PermissionType> optional = Arrays.stream(values()).filter(type -> type.getKey().equalsIgnoreCase(key)).findFirst();
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new RuntimeException("Fail to find Permission Type");
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFriendlyType() {
		return friendlyType;
	}

	public void setFriendlyType(String friendlyType) {
		this.friendlyType = friendlyType;
	}
}
