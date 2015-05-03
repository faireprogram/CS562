package org.stevens.cs562.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.SqlSentence;
import org.stevens.cs562.sql.visit.AggregateExpressionVisitorImpl;


public class GeneratorHelper {
	
	public static String BLANK = "\n";
	
	/**
	 * Generate Line
	 */
	public static String gl(String param, int incent) {
		return ind(incent) + param + "\n";
	}
	
	/**
	 * Generate Comment
	 */
	public static String gc(String content, int incent) {
		String ince = ind(incent);
		String s1 = ince + "//----------------------------------------------------------------------\n";
		String s2 = ince + "//" + content + "\n";
		return s1 + s2 + s1;
	}
	
	/**
	 * Generate Incent
	 */
	public static String ind(int incent) {
		String ince = "";
		for(int i = 0; i < incent; i++) {
			ince += "\t";
		}
		return ince;
	}
	
	//----------------------------------------- used to help retrieve whatever you want
	/**
	 * getAllAggregateExpression from SQL
	 */
	public static void getAllAggregateExpression(AggregateExpressionVisitorImpl visitor, SqlSentence  sqlsentence) {
		/* find the Aggression Expression and Add it to the set*/
		for(Expression exp : sqlsentence.getSuchThatElement().getSuch_that_expressions()) {
			visitor.visit(exp);
		}
		for(Expression exp : sqlsentence.getHavingElement().getHaving_expressions()) {
			visitor.visit(exp);
		}
		
		// SelectElement
		for(Expression project_items : sqlsentence.getSelectElement().getProjectItems()) {
			if(project_items instanceof AggregateExpression) {
				visitor.getAggregate_expression().add((AggregateExpression)project_items);
			}
			if(project_items instanceof ComparisonAndComputeExpression) {
				visitor.visit(project_items);
			}
		}
	}
	
	
	/**
	 * Retrieve back the type via your column, connection from data, and the relation of sqlsentence
	 */
	public static String find_type(String column, Connection connection, SqlSentence sqlsentence) throws SQLException {
		String type = Constants.STRING_TYPE;
		try {
			if(sqlsentence.getAttributes_type().get(column) != null) {
				return sqlsentence.getAttributes_type().get(column);
			}
			String search_type = String.format(Constants.SEARCHING_TYPE, "'sales'", "'" + column + "'");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(search_type);
			rs.next();
			if(rs.getString(1).toLowerCase().contains("char")) {
				type = Constants.STRING_TYPE;
			} 
			if(rs.getString(1).toLowerCase().contains("int")) {
				type = Constants.INTERGER_TYPE;
			}
			sqlsentence.getAttributes_type().put(column, type);
		} catch (SQLException e) {
			System.out.println("ERROR HAPPENS WITH YOUR Column: " + column);
			throw e;
		}
		return type;
	}
	
	
	
}
