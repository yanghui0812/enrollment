package com.enroll.rest.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.enroll.common.AppConstant;
import com.enroll.rest.dto.RestFieldValue;
import com.enroll.rest.dto.RestRequest;

public final class Signature {

	private static final Logger LOGGER = LogManager.getLogger(Signature.class);

	@SuppressWarnings("unchecked")
	public static String getSign(Object obj) {
		List<String> nameValuePairList = new ArrayList<String>();
		List<RestFieldValue> fieldValueList = null;
		List<Field> fieldList = getClassFieldList(obj);
		try {
			for (Field field : fieldList) {
				field.setAccessible(true);
				if (StringUtils.equals(field.getName(), "data")) {
					fieldValueList = (List<RestFieldValue>) field.get(obj);
					continue;
				}
				if (StringUtils.equals(field.getName(), "fieldErrors")) {
					fieldValueList = (List<RestFieldValue>) field.get(obj);
					continue;
				}
				if (!StringUtils.equals(field.getName(), "signature") && field.get(obj) != null
						&& !StringUtils.EMPTY.equals(field.get(obj))) {
					nameValuePairList.add(field.getName() + AppConstant.EQUAL_SIGN + field.get(obj));
				}
			}
			nameValuePairList.addAll(getDataPayload(fieldValueList));
			Collections.sort(nameValuePairList);
			String result = nameValuePairList.stream().collect(Collectors.joining(AppConstant.AND_SIGN));
			result += "&key=" + AppConstant.WECHAT_KEY;
			LOGGER.info("Name and value pairs before HmacSHA256 are:" + result);
			result = HMACSHA256(result.getBytes("UTF-8"), AppConstant.WECHAT_KEY.getBytes("UTF-8")).toUpperCase();
			LOGGER.info("Signature is:" + result);
			return result;
		} catch (Exception e) {
			LOGGER.error("Error happened when signing:" + e);
		}
		return StringUtils.EMPTY;
	}
	
	@SuppressWarnings("rawtypes")
	private static List<Field> getClassFieldList(Object obj) {
		Class cls = obj.getClass();
		List<Field> fieldList = new ArrayList<>();
		fieldList.addAll(Arrays.asList(cls.getDeclaredFields()));
		fieldList.addAll(Arrays.asList(cls.getSuperclass().getDeclaredFields()));
		return fieldList;
	}

	private static List<String> getDataPayload(List<RestFieldValue> list) {
		List<String> result = new ArrayList<String>();
		if (CollectionUtils.isEmpty(list)) {
			return result;
		}
		list.stream().forEach(restField -> {
			if (StringUtils.isNotBlank(restField.getValue())) {
				result.add(restField.getName() + AppConstant.EQUAL_SIGN + restField.getValue());
			}
		});
		return result;
	}

	public static boolean checkSignature(Object obj) {
		String signature = ((RestRequest) obj).getSignature();
		String signForAPIRequest = null;
		try {
			signForAPIRequest = getSign(obj);
		} catch (Exception e) {
			LOGGER.error("Error happened when signing request " + e);
			return false;
		}
		if (!StringUtils.equals(signature, signForAPIRequest)) {
			LOGGER.info("Request's signature could be modified!!!");
			return false;
		}
		LOGGER.info("Request's signature is good!");
		return true;
	}

	public static String HMACSHA256(byte[] data, byte[] key) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
			return byte2hex(mac.doFinal(data));
		} catch (Exception e) {
			LOGGER.error("Error happened when init HMAC" + e);
			throw new RuntimeException(e);
		}
	}

	public static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}
}