package org.stevens.cs562.utils;


public class GeneratorHelper {
	
	public static String BLANK = "\n";
	
	/**
	 * Generate Line
	 */
	public static String gl(String param, int incent) {
		return ind(incent) + param + "\n";
	}
	
	/**
	 * Generate Comment
	 */
	public static String gc(String content, int incent) {
		String ince = ind(incent);
		String s1 = ince + "//----------------------------------------------------------------------\n";
		String s2 = ince + "//" + content + "\n";
		return s1 + s2 + s1;
	}
	
	/**
	 * Generate Incent
	 */
	public static String ind(int incent) {
		String ince = "";
		for(int i = 0; i < incent; i++) {
			ince += "\t";
		}
		return ince;
	}
}
