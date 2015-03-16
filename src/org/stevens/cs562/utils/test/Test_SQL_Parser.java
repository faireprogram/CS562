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
		SqlSentence ss = new SqlSentence(ResourceHelper.readFromFile());
		System.out.println(ss);
//		 String stringToSearch = "Four score and seven years ago our fathers ...";
//	     
//		    // this won't work because the pattern is in upper-case
//		    System.out.println("Try 1: " + stringToSearch.matches(".*SEVEN.*"));
//		     
//		    // the magic (?i:X) syntax makes this search case-insensitive, so it returns true
//		    System.out.println("Try 2: " + stringToSearch.matches("?i:.*SEVEN.*"));
//		String sql = "a<>b and  zz <= x";
//		String  f = ">=|<=|<>|>|<|=";
//		String[] ss = sql.split(f);
//		
//	
//		System.out.println(sql.contains("<>"));
	}

}
