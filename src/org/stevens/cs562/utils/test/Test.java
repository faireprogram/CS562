package org.stevens.cs562.utils.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.stevens.cs562.sql.sqlimpl.SqlSentence;
import org.stevens.cs562.utils.SQLStringParsers;

public class Test {
	
	
	public static String s = " SELECT ty ,zy,  xz,th "
			+ "from sales C, sales D "
			+ "where C.X='1' "
			+ "group by C.X, D.Y : X, Y "
			+ "suchthat X.start_date < '192/1687/200  and Y.start_data > '899222' "
			+ "having count(X.startdate) > 5 ;";
	
	private static String readLine(String format) throws IOException {
	    
	    System.out.print(String.format(format, null));
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
	            System.in));
	    return reader.readLine();
	}
	
	public static void main(String [ ] args) throws IOException {
//		RelationalAnalysis rs = new RelationalAnalysis();
		SqlSentence ss = new SqlSentence(Test.s);
		System.out.println("Hello Wordld") ; 
		
		
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
