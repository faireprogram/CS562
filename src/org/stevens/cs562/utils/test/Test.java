package org.stevens.cs562.utils.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.stevens.cs562.utils.SQLStringParsers;

public class Test {
	
	private String ss = "select * from 11";
	
	private static String readLine(String format) throws IOException {
	    
	    System.out.print(String.format(format, null));
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
	            System.in));
	    return reader.readLine();
	}
	
	public static void main(String [ ] args) throws IOException {
//		RelationalAnalysis rs = new RelationalAnalysis();
		SQLStringParsers.parseString();
		System.out.println("Hello World") ; 
		
		
//        while (true) {
//
//            Pattern pattern = 
//            Pattern.compile(readLine("%nEnter your regex: "));
//
//            Matcher matcher = 
//            pattern.matcher(readLine("Enter input string to search: "));
//
//            boolean found = false;
//            while (matcher.find()) {
//            	Object[] objs = new Object[]{ matcher.group(),
//                        new Integer(matcher.start()),
//                       new Integer( matcher.end())};
//            	 System.out.print(String.format("I found the text" +
//                    " \"%s\" starting at " +
//                    "index %d and ending at index %d.%n",
//                    objs));
//                found = true;
//            }
//            if(!found){
//            	System.out.print("No match found.%n");
//            }
//        }
	}
}
