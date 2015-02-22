package org.stevens.cs562.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLStringParsers {
	/**
	 * 
	 */
	public static String[] parseString(String sql) {
		Pattern p = Pattern.compile(Constants.SQL_REGULAR_EXPRESSION, Pattern.CASE_INSENSITIVE);
		Matcher matchers = p.matcher(sql);
		String[] tmp = new String[6];
		if(matchers.find()) {

			tmp[0] = matchers.group("selectcause");
			tmp[1] = matchers.group("fromcause");
			tmp[2] = matchers.group("wherecause");
			tmp[3] = matchers.group("groupby");
			tmp[4] = matchers.group("suchthat");
			if(matchers.group("having") != null ) {
				tmp[5] = matchers.group("having");
			} else if(matchers.group("having1") != null){
				tmp[5] = matchers.group("having1");
			} else {
				tmp[5] = null;
			}
			
			System.out.println(matchers.group("selectcause"));
			System.out.println(matchers.group("fromcause"));
			System.out.println(matchers.group("wherecause"));
			System.out.println(matchers.group("groupby"));
			System.out.println(matchers.group("suchthat"));
			System.out.println(matchers.group("having"));
			
		}
		return tmp;
	}
			
		
}
