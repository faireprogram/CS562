package org.stevens.cs562.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		// || s1.trim().matches("^\\d*\\.\\d*$") do not support float
		if(s1.trim().matches("^\\d+$") ) {
			return true;
		}
		return false;
	}
	
	public static boolean isString(String s1) {
		if(s1 == null) {
			return false;
		}
		if(s1.trim().matches("^'.+?'$") || s1.trim().matches("\".+?\"$")) {
			return true;
		}
		return false;
	}
	
	public static boolean isCompute(String s1) {
		if(s1 == null) {
			return false;
		}
		String compute_regex = "\\+|-|\\*|/";
		String[] strings = s1.split(compute_regex);
		if(strings.length > 1) {
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
	
//	public static List<String> splitStringFromSQL(String sql)  {
//		
//		
//	}
	
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
	
	public static String[] splitSqlIntoStringArray(String a) {
		String[] results = new String[6];
		int from_begin = getStartMatch(a, "from");
		int where_begin =getStartMatch(a, "where");
		int group_begin = getStartMatch(a, "group by");
		int suchthat_begin = getStartMatch(a, "such that");
		int having_begin = getStartMatch(a, "having");
		
		int select_end = getEndMatch(a, "select");
		int from_end = getEndMatch(a, "from");
		int where_end =getEndMatch(a, "where");
		int group_end = getEndMatch(a, "group by");
		int suchthat_end = getEndMatch(a, "such that");
		int having_end = getEndMatch(a, "having");
		
		String select_part = a.substring(select_end, from_begin);
		String from_part = "";
		if(where_begin != -1) {
			from_part = a.substring(from_end, where_begin);
		} else if(group_begin != -1) {
			from_part = a.substring(from_end, group_begin);
		} else {
			from_part = a.substring(from_end, a.length());
		}
		
		String where_part = "";
		if(where_begin != -1) {
			if(group_begin != -1) {
				where_part = a.substring(where_end, group_begin);
			} else {
				where_part = a.substring(where_end, a.length());
			}
		} 
		
		String group_part = "";
		if(group_begin != -1) {
			if(suchthat_begin != -1) {
				group_part = a.substring(group_end, suchthat_begin);
			} else if(having_begin != -1){
				group_part = a.substring(group_end, having_begin);
			} else {
				group_part = a.substring(group_end,  a.length());
			}
		} 
		
		String suchthat_part = "";
		if(suchthat_begin != -1) {
			if(having_begin != -1) {
				suchthat_part = a.substring(suchthat_end, having_begin);
			} else {
				suchthat_part = a.substring(suchthat_end, a.length());
			}
		} 
		
		String having_part = "";
		if(having_begin != -1) {
			having_part = a.substring(having_end, a.length());
		} 
		
		results[0] = select_part.trim();
		results[1] = from_part.trim();
		results[2] = where_part.trim();
		results[3] = group_part.trim();
		results[4] = suchthat_part.trim();
		results[5] = having_part.trim();
		
		return results;
	}
	
	private static int getEndMatch(String a, String regex) {
		 Pattern pattern3 = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		 Matcher matcher = pattern3.matcher(a);
		while(matcher.find()) {
			return matcher.end();
		}
		return -1;
	}
	
	private static int getStartMatch(String a, String regex) {
		 Pattern pattern3 = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		 Matcher matcher = pattern3.matcher(a);
		while(matcher.find()) {
			return matcher.start();
		}
		return -1;
	}
}
