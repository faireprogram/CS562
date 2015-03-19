package org.stevens.cs562.utils.test;

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
		
//		String s = "sum( X.quant)/sum( Y.quant)";
//		System.out.println(s.contains("/"));
	}

}
