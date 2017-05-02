package com.enroll.security.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.enroll.security.entity.User;
public final class SessionContextHolder {

	private static User retrieveUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return (User) principal;
		}
		return null;
	}

	public static String getCurrentUserName() {
		return retrieveUser().getUsername();
	}

	public static String getCurrentUserId() {
		return retrieveUser().getId();
	}
};