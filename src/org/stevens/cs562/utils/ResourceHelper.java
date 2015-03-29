package org.stevens.cs562.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.stevens.cs562.utils.test.Test_SQL_Parser;


public class ResourceHelper {

	private static Properties prop;
	
	public static String readFromFile() {
		String result = "";
		 
		BufferedReader br = null; 
		try {
			 InputStream stream = ResourceHelper.class.getClassLoader().getResourceAsStream(getProperties().getProperty("script_path"));
			String sCurrentLine = "";
			
			br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			
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
	
	public static String readPOM(String name) {
		String result = "";
		 
		BufferedReader br = null; 
		try {
			 InputStream stream = ResourceHelper.class.getClassLoader().getResourceAsStream("pom-template");
			String sCurrentLine = "";
			
			br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			
			while ((sCurrentLine = br.readLine()) != null) {
				result = result + sCurrentLine.replaceAll("MAINCLASSNAME", name) + "\n";
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
	
	public static String getValue(String key){
		try {
			return getProperties().getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static Connection getConnection()  {
		Connection conn = null;
		try 
		{
			String usr = ResourceHelper.getValue("usrname");
			String pwd =ResourceHelper.getValue("password");;
			String url =ResourceHelper.getValue("postsql_url");
			conn = DriverManager.getConnection(url, usr, pwd); 
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, usr, pwd); 
		} 

		catch(Exception e) 
		{
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}
		
	
		return conn;
	}
}
