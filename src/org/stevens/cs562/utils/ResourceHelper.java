package org.stevens.cs562.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.stevens.cs562.utils.test.Test_SQL_Parser;


public class ResourceHelper {

	private static Properties prop;
	
	public static String readFromFile() {
		String result = "";
		 
		BufferedReader br = null; 
		try {
			
			URL url = Test_SQL_Parser.class.getClassLoader().getResource(getProperties().getProperty("script_path"));
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
	
	public static Properties getProperties() throws IOException {
		if(prop == null) {
			prop = new Properties();
			InputStream stream = Test_SQL_Parser.class.getClassLoader().getResourceAsStream("configure.properties");
			prop.load(stream);
		}
		return prop;
	}
	
	public static String getValue(String key) throws IOException {
		return getProperties().getProperty(key);
	}

}
