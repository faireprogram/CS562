package org.stevens.cs562.utils;

public class Constants {

	/**
	 * SQL_REGULAR_EXPRESSION
	 */
	public static String SQL_REGULAR_EXPRESSION = "^\\s*\\bselect\\b\\s+(?<selectcause>.+)\\s+from\\s+(?<fromcause>.+?)(\\s+where\\s(?<wherecause>.+?)"
			+ "("
			+ "\\s+group\\s+by\\s+(?<groupby>.+?)"
			+ "("
				+ "(\\s+having\\s+(?<having1>.+?))?\\s+such\\s+that\\s+(?<suchthat>.+?)(\\s+having\\s+(?<having>.+?))?"
			+ ")?"
		+ ")?)?\\s*;??\\s*$";
	
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
	
	public static String CHARACTER_REPLACEMENT = "_____REPLACEMENT";
	
	/**
	 * GENERATE_CODE_MF_TABLE
	 */
	public static String GENERATE_CODE_MF_TABLE = "MFtable";
	
	/**
	 * GENERATE_CODE_MF_MAIN
	 */
	public static String GENERATE_CODE_MF_MAIN = "MFmain";
	
	/**
	 * GENERATE_CODE_MF_TABLE
	 */
	public static String GENERATE_CODE_EMF_TABLE = "EMFtable";
	
	/**
	 * GENERATE_CODE_MF_MAIN
	 */
	public static String GENERATE_CODE_EMF_MAIN = "EMFmain";
	
	/**
	 * SEARCHING_TYPE
	 */
	public static String SEARCHING_TYPE ="select data_type, character_maximum_length from information_schema.columns where table_name = %s and column_name = %s ;";
	
	public static String STRING_TYPE = "String";
	
	public static String INTERGER_TYPE = "int";
	
	public static String OUT_PUT_PATH = "D:\\Project\\Java\\CS562\\Test\\src\\";
	
	public static String GROUPING_ZERO = "Group 0";
	
	public static String GENERATE_EMF = "EMF";
	
	public static String GENERATE_MF = "MF";
	
	public static String RELATION_ALGEBRA = "relation_algebra_output.txt";
	
	public static String MESSAGE_NO_AGGREGATES = "// No Aggregates for Variable:  ";
}
