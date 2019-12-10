package com.Yang.common.utils.nullUtil;

import java.util.Collection;
import java.util.Map;

/**
 * 空判断
 * 
 * @author openPriestling
 * @name com.iandun.andun.util.IsNull
 * @date 2017年3月13日上午11:09:06
 */
public class IsNull {
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map) obj).isEmpty();

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}

	public static String NullToString(String source, String defaultString) {
		if (isNullOrEmpty(source)) {
			return defaultString;
		}
		return source;
	}

	public static int NullToInt(Integer source, int defaultInt) {
		if (isNullOrEmpty(source)) {
			return defaultInt;
		}
		return source;
	}
}
