package org.stevens.cs562.utils;

public class StringBuilder {

	public static boolean isEqual(String s1, String s2) {
		if(s1 == null && s2 == null) {
			return true;
		}
		if(s1 == null || s2 == null) {
			return false;
		}
		return s1.equals(s2);
	}
	
	public static boolean isEmpty(String s1) {
		if(s1 == null) {
			return true;
		}
		if(s1.trim().equals("")) {
			return true;
		}
		return false;
	}
	
	public static boolean isNumber(String s1) {
		if(s1 == null) {
			return false;
		}
		if(s1.trim().matches("^\\d+$")) {
			return true;
		}
		return false;
	}
	
	public static int toInt(String s1) {
		if(s1 == null) {
			return 0;
		} else {
			return Integer.valueOf(s1.trim());
		}
	}
	
}
