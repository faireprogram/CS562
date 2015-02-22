package org.stevens.cs562.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLStringParsers {

	public static String s = "the SELECT ty ,zy,  xz,th from sales C, sales D where C.X='1';";
	
	/**
	 * 
	 */
	public static boolean parseString() {
		Pattern p = Pattern.compile("\\s*select\\s+(?<selectcause>.+)\\s+from\\s+(?<fromcause>.+)\\s+where\\s(?<wherecause>.+)\\s*;", Pattern.CASE_INSENSITIVE);
		Matcher matchers = p.matcher(s);
		while(matchers.find()) {
			System.out.println("I found the text");
			System.out.println(matchers.group("selectcause"));
			System.out.println(matchers.group("fromcause"));
			System.out.println(matchers.group("wherecause"));
			matchers.group();
			matchers.start();
			matchers.end();
			matchers.groupCount();
			System.out.println("hellow world");
			
		}
		return true;
	}
			
		
}
