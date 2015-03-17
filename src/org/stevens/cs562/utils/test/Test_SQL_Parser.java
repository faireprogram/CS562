package org.stevens.cs562.utils.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.stevens.cs562.sql.sqlimpl.SqlSentence;
import org.stevens.cs562.utils.ResourceHelper;
import org.stevens.cs562.utils.StringBuilder;

public class Test_SQL_Parser {

/*
	select product, month, avg(X.quantity),
	avg(Y.quantity)
	from Sales
	where year=¡®¡®1997¡¯¡¯
	group by product, month; X , Y
	such that X.product=product and X.month>month, Y.product=product and Y.month<month
 
 */
	
	public Test_SQL_Parser() {
	}
	
	public static void main(String[] args) {
//		SqlSentence ss = new SqlSentence(ResourceHelper.readFromFile());
//		System.out.println(ss);
		
		String a = "(a dd s  )  and(c> avg(bb)) s (a < SUM(  dd))";
		StringBuilder.splitStringFromParenthesis(a);
//		
//		 String s="word1 AND word2 OR (word3 AND (word4 OR word5)) AND word6";
//
//		    String regEx="(?:[^AND(]|\\([^)]*\\))+";
//		     Pattern pattern3 = Pattern.compile(regEx, Pattern.DOTALL);
//		     Matcher matcher3 = pattern.matcher(s); 
//		     
//		     while (matcher3.find()) {             
//		         System.out.println("Found the text \"" + matcher3.group() + "\" starting at " + matcher3.start() + " index and ending at index " + matcher3.end());         
//		     } 
//		while(match.find()) {
//			System.out.println(match.group(1));
//		} //avg|sum|count|max|min
//		
//		String str = "progradmcreek";
//		Pattern p = Pattern.compile(".*(?<!program)(creek).*");
//		Matcher m = p.matcher(str);
//		 while(m.find()) {
//			 System.out.println(m.group(1));
//		 }
//		if(m.matches()){
//			System.out.println("Match!");
//		}else{
//			System.out.println("No");
//		}
	}

}
