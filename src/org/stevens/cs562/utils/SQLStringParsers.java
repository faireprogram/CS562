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
		Matcher match = p.matcher(s);
		while(match.find()) {
			System.out.println("I found the text");
			System.out.println(match.group("selectcause"));
			System.out.println(match.group("fromcause"));
			System.out.println(match.group("wherecause"));
			match.group();
			match.start();
			match.end();
			match.groupCount();
			
		}
		return true;
	}
			
		
}
