package org.stevens.cs562.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
	
	public static List<String> splitStringFromParenthesis(String sql) {
		Stack<Integer> index_left_stack = new Stack<Integer>();
		List<String> results = new ArrayList<String>();
		for(int i = 0; i < sql.length(); i++) {
			if(sql.charAt(i) == '(') {
				index_left_stack.push(i);
			}
			if(sql.charAt(i) == ')') {
				int last_position = index_left_stack.pop();
				String str_back = sql.substring(0,last_position);
				if(!str_back.matches("(?i:.*?(avg|count|max|min|sum)\\s*$)")) {
					results.add(sql.substring(last_position+1, i));
				}
				
			}
		}
		return results;
	}
	
}
