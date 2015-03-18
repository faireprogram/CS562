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
		
//		String a = ResourceHelper.readFromFile().toLowerCase();
//		StringBuilder.splitStringFromParenthesis(a);
//		
//		int select_begin = getStartMatch(a, "select");
//		int from_begin = getStartMatch(a, "from");
//		int where_begin =getStartMatch(a, "where");
//		int group_begin = getStartMatch(a, "group by");
//		int suchthat_begin = getStartMatch(a, "such that");
//		int having_begin = getStartMatch(a, "having");
//		
//		int select_end = getEndMatch(a, "select");
//		int from_end = getEndMatch(a, "from");
//		int where_end =getEndMatch(a, "where");
//		int group_end = getEndMatch(a, "group by");
//		int suchthat_end = getEndMatch(a, "such that");
//		int having_end = getEndMatch(a, "having");
//		
//		String select_part = a.substring(select_end, from_begin);
//		String from_part = "";
//		if(where_begin != -1) {
//			from_part = a.substring(from_end, where_begin);
//		} else if(group_begin != -1) {
//			from_part = a.substring(from_end, group_begin);
//		} else {
//			from_part = a.substring(from_end, a.length());
//		}
//		
//		String where_part = "";
//		if(where_begin != -1) {
//			if(group_begin != -1) {
//				where_part = a.substring(where_end, group_begin);
//			} else {
//				where_part = a.substring(where_end, a.length());
//			}
//		} 
//		
//		String group_part = "";
//		if(group_begin != -1) {
//			if(suchthat_begin != -1) {
//				group_part = a.substring(group_end, suchthat_begin);
//			} else if(having_begin != -1){
//				group_part = a.substring(group_end, having_begin);
//			} else {
//				group_part = a.substring(group_end,  a.length());
//			}
//		} 
//		
//		String suchthat_part = "";
//		if(suchthat_begin != -1) {
//			if(having_begin != -1) {
//				suchthat_part = a.substring(suchthat_end, having_begin);
//			} else {
//				suchthat_part = a.substring(suchthat_end, a.length());
//			}
//		} 
//		
//		String having_part = "";
//		if(having_begin != -1) {
//			having_part = a.substring(having_end, a.length());
//		} 
//		
//		
//		System.out.println(select_part);
//		System.out.println(from_part);
//		System.out.println(where_part);
//		System.out.println(group_part);
//		System.out.println(suchthat_part);
//		System.out.println(having_part);
//		
//		System.out.println(select_begin);
//		System.out.println(from_begin);
//		System.out.println(where_begin);
//		System.out.println(group_begin);
//		System.out.println(suchthat_begin);
//		System.out.println(having_begin);
		
	
		
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
