package com.enroll.rest.utils;

import java.util.concurrent.ThreadLocalRandom;

public final class NonceUtils {

	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String getNonceString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 32; ++i) {
			int number = ThreadLocalRandom.current().nextInt(62);
			sb.append(ALPHABET.charAt(number));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(NonceUtils.getNonceString());
	}
}
