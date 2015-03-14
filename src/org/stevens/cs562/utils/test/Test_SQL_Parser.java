package org.stevens.cs562.utils.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;

import org.stevens.cs562.sql.sqlimpl.SqlSentence;

public class Test_SQL_Parser {

/*
	select product, month, avg(X.quantity),
	avg(Y.quantity)
	from Sales
	where year=¡®¡®1997¡¯¡¯
	group by product, month; X , Y
	such that X.product=product and X.month>month, Y.product=product and Y.month<month
 
 */
	
	public static String readFromFile() {
		String result = "";
		 
		BufferedReader br = null; 
		
		try {
			URL url = Test_SQL_Parser.class.getClassLoader().getResource("Sql");
			String sCurrentLine = "";
			br = new BufferedReader(new FileReader(url.getPath()));
			
			while ((sCurrentLine = br.readLine()) != null) {
				result = result + sCurrentLine;
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}
	
	public Test_SQL_Parser() {
	}
	
	public static void main(String[] args) {
		SqlSentence ss = new SqlSentence(readFromFile());
	}

}
