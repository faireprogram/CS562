package org.stevens.cs562.utils.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import org.stevens.cs562.code.CodeGenerator;
import org.stevens.cs562.sql.AggregateOperator;
import org.stevens.cs562.sql.ComparisonAndComputeOperator;
import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.AttributeVariable;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.GroupByElement;
import org.stevens.cs562.sql.sqlimpl.GroupingVaribale;
import org.stevens.cs562.sql.sqlimpl.NullVariable;
import org.stevens.cs562.sql.sqlimpl.SelectElement;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;
import org.stevens.cs562.sql.sqlimpl.SqlSentence;
import org.stevens.cs562.sql.sqlimpl.SuchThatElement;
import org.stevens.cs562.sql.visit.AggregateExpressionVisitorImpl;
import org.stevens.cs562.sql.visit.RelationBuilder;
import org.stevens.cs562.sql.visit.RelationBuilderVisitor;
import org.stevens.cs562.utils.DynamicCompiler;
import org.stevens.cs562.utils.ResourceHelper;

public class TestGenerator {
	
	public static void main(String[] strings) throws SQLException, IOException {
		
		SqlSentence sqlsetence = new SqlSentence(ResourceHelper.readFromFile());

		
		Connection connection = getConnection();
		CodeGenerator generate = new CodeGenerator(sqlsetence, connection);
		generate.generate();
		connection.close();
//		
//		/*
//		 * dynamic 
//		 */
//		DynamicCompiler compiler = new DynamicCompiler();
//		compiler.compileAndRun();
	}
	
	/**
	 * @return
	 * @throws SQLException 
	 */
	private static Connection getConnection() throws SQLException {
		String usr ="postgres";
		String pwd ="zw198787";
		String url ="jdbc:postgresql://localhost:5432/test";
		try 
		{
			Class.forName("org.postgresql.Driver");
		} 

		catch(Exception e) 
		{
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}
		
		Connection conn = DriverManager.getConnection(url, usr, pwd); 
		return conn;
	}

}
