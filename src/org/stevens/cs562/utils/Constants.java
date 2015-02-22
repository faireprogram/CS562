package org.stevens.cs562.utils;

public class Constants {

	/**
	 * SQL_REGULAR_EXPRESSION
	 */
	public static String SQL_REGULAR_EXPRESSION = "^\\s*\\bselect\\b\\s+(?<selectcause>.+)\\s+from\\s+(?<fromcause>.+?)(\\s+where\\s(?<wherecause>.+?)"
			+ "("
			+ "\\s+group\\s+by\\s+(?<groupby>.+?)"
			+ "("
				+ "(\\s+having\\s+(?<having1>.+?))?\\s+suchthat\\s+(?<suchthat>.+?)(\\s+having\\s+(?<having>.+?))?"
			+ ")?"
		+ ")?)?\\s*;\\s*$";
	
	/**
	 * KEY_WORD_SELECT
	 */
	public static String KEY_WORD_SELECT ="SELECT"; 
	
	/**
	 * KEY_WORD_FROM
	 */
	public static String KEY_WORD_FROM ="FROM"; 
	
	/**
	 * KEY_WORD_WHERE
	 */
	public static String KEY_WORD_WHERE ="WHERE"; 
	
	/**
	 * KEY_WORD_GROUP_BY
	 */
	public static String KEY_WORD_GROUP_BY ="GROUP_BY";
	
	/**
	 * KEY_WORD_SUCHTHAT
	 */
	public static String KEY_WORD_SUCHTHAT ="SUCHTHAT";
	
	/**
	 * KEY_WORD_HAVING
	 */
	public static String KEY_WORD_HAVING ="HAVING";
	
	/**
	 * ERROR_SYNTAX_SELECT
	 */
	public static int ERROR_SYNTAX_SELECT = 0;
	
	/**
	 * ERROR_SYNTAX_FROM
	 */
	public static int ERROR_SYNTAX_FROM = 1;
	
	/**
	 * ERROR_SYNTAX_WHERE
	 */
	public static int ERROR_SYNTAX_WHERE = 2;
	
	/**
	 * ERROR_SYNTAX_GROUP_BY
	 */
	public static int ERROR_SYNTAX_GROUP_BY = 3;
	
	/**
	 * ERROR_SYNTAX_SUCHTHAT
	 */
	public static int ERROR_SYNTAX_SUCHTHAT = 4;
	
	/**
	 * ERROR_SYNTAX_Having
	 */
	public static int ERROR_SYNTAX_Having = 5;
	
}
