package org.stevens.cs562.utils.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.SqlSentence;
import org.stevens.cs562.sql.visit.RelationBuilder;
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
		SqlSentence ss = new SqlSentence(ResourceHelper.readFromFile());
		System.out.println(ss);
		
//		String s = "sum( X.quant)/sum( Y.quant)";
//		System.out.println(s.contains("/"));
	}

}
