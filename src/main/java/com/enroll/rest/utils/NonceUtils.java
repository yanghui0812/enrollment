package com.enroll.rest.utils;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import com.enroll.common.DateUtils;

public final class NonceUtils {
	
	public static String getNonceString() {
		return ThreadLocalRandom.current().nextLong(100000000) + LocalDateTime.now().format(DateUtils.YYYYMMDDHHMMSSSSS) + ThreadLocalRandom.current().nextLong(10000000);
	}
	
	public static void main(String[] args) {
		System.out.println(NonceUtils.getNonceString());
	}
}
