package org.stevens.cs562.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.stevens.cs562.utils.test.Test_SQL_Parser;

public class ResourceHelper {

	public static String readFromFile() {
		String result = "";
		 
		BufferedReader br = null; 
		
		try {
			URL url = Test_SQL_Parser.class.getClassLoader().getResource("Sql1");
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

}
