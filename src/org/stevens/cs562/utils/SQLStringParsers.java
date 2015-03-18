package org.stevens.cs562.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.stevens.cs562.sql.AggregateOperator;
import org.stevens.cs562.sql.ComparisonAndComputeOperator;
import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.AttributeVariable;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.FromElement;
import org.stevens.cs562.sql.sqlimpl.GroupByElement;
import org.stevens.cs562.sql.sqlimpl.GroupingVaribale;
import org.stevens.cs562.sql.sqlimpl.HavingElement;
import org.stevens.cs562.sql.sqlimpl.IntegerExpression;
import org.stevens.cs562.sql.sqlimpl.SelectElement;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;
import org.stevens.cs562.sql.sqlimpl.SuchThatElement;
import org.stevens.cs562.sql.sqlimpl.WhereElement;

public class SQLStringParsers {
	
	
	/**
	 * 
	 */
	public static String[] parseString(String sql) {
		return StringBuilder.splitSqlIntoStringArray(sql);
	}
	
	/**
	 * parseStringToSelectElement
	 * @param sql
	 * @return
	 */
	public static void parseStringToSelectElement(String sql, SelectElement element) {
		if(StringBuilder.isEmpty(sql)) {
			element.setExist(false);
			return;
		}
		
		String[] strings = sql.split(",");
		for(int i = 0; i < strings.length; i++) {
			strings[i] = strings[i].trim();
			element.getProjectItems().add(processExpression(strings[i], element.getSelfSentce().getGrouping_variable_dic()));
		}
		System.out.println(element);
	}
	
	public static void parseStringToFromElement(String sql, FromElement element) {
		// NEED TO DO
	}
	
	public static void parseStringToWhereElement(String sql, WhereElement element) {
		if(StringBuilder.isEmpty(sql)) {
			element.setExist(false);
			return;
		}
		String[] strings = sql.split(",");
		
		for(int i = 0; i < strings.length; i++) {
			element.getWhere_expressions().add(generateComputeOrComparisonExpression(strings[i], element.getSelfSentce().getGrouping_variable_dic()));
		}
		
	}
	
	public static void parseStringToGroupByElement(String sql, GroupByElement element) {
		if(StringBuilder.isEmpty(sql)) {
			element.setExist(false);
			return;
		}
		String[] strings = sql.split(":");
		String[] attrs = null;
		attrs = strings[0].split(",");
		
		for(int i =0; i < attrs.length; i++) {
			attrs[i] = attrs[i].trim();
			element.getGrouping_attributes().add(generateAttributeVariable(attrs[i], Constants.GROUPING_ZERO, element.getSelfSentce().getGrouping_variable_dic()));
		}
		
		if(strings.length == 2) {
			String[] g_variabls = strings[1].split(",");
			for(int i =0; i < g_variabls.length; i++) {
				g_variabls[i] = g_variabls[i].trim();
				GroupingVaribale tmp = new GroupingVaribale(g_variabls[i], String.valueOf(i+1));
				element.getSelfSentce().getGrouping_variable_dic().put(g_variabls[i], tmp);
			}
			element.getSelfSentce().getGrouping_variable_dic().get("Y");
		}
	}
	
	public static void parseStringToSuchThatElement(String sql, SuchThatElement element) {
		if(StringBuilder.isEmpty(sql)) {
			element.setExist(false);
			return;
		}
		String[] strings = sql.split(",");
		
		for(int i = 0; i < strings.length; i++) {
			List<String> list_string = StringBuilder.splitStringFromParenthesis(strings[i]);
			if(list_string.size() == 0) {
				element.getSuch_that_expressions().add(generateComputeOrComparisonExpression(strings[i], element.getSelfSentce().getGrouping_variable_dic()));
			} else {
				for(String str : list_string) {
					element.getSuch_that_expressions().add(generateComputeOrComparisonExpression(str, element.getSelfSentce().getGrouping_variable_dic()));
				}
			}
			
		}
		System.out.println(element);
	}
	
	public static void parseStringToHavingElement(String sql, HavingElement element) {
		if(StringBuilder.isEmpty(sql)) {
			element.setExist(false);
			return;
		}
		String[] strings = sql.split(",");
		
		for(int i = 0; i < strings.length; i++) {
			element.getHaving_expressions().add(generateComputeOrComparisonExpression(strings[i], element.getSelfSentce().getGrouping_variable_dic()));
		}
		
	}
	
	/**
		used to process all the Expression 
	 */
	private static Expression processExpression(String express, HashMap<String, GroupingVaribale> dic) {
		Expression final_expression = null;
		if(express.trim().matches("(?i:avg\\(.+?\\)|count\\(.+?\\)|min\\(.+?\\)|max\\(.+?\\)|sum\\(.+?\\))")) {
			final_expression = processAggregateExpression(express.trim(), dic);
		} else if(StringBuilder.isNumber(express)) {
			final_expression = new IntegerExpression(Integer.valueOf(express.trim()));
		} else {
			final_expression = processSimpleExpression(express.trim(), dic);
		}
		return final_expression;
	}
	
	
	/*
	 * AggressionExpression generator
	 */
	private static AggregateExpression processAggregateExpression(String express, HashMap<String, GroupingVaribale> dic) {
		String value = express.replaceAll("(?i:avg\\((.+?)\\)|count\\((.+?)\\)|min\\((.+?)\\)|max\\((.+?)\\)|sum\\((.+?)\\))", "$1$2$3$4$5").trim();
		
		String operatorSign = express.replaceAll("(?i:(avg)\\(.+?\\)|(count)\\(.+?\\)|(min)\\(.+?\\)|(max)\\(.+?\\)|(sum)\\(.+?\\))", "$1$2$3$4$5");

		AggregateOperator operator = AggregateOperator.AVERAGE;
		
		if(operatorSign.equalsIgnoreCase(AggregateOperator.AVERAGE.getName())) {
			operator = AggregateOperator.AVERAGE;
		}
		if(operatorSign.equalsIgnoreCase(AggregateOperator.COUNT.getName())) {
			operator = AggregateOperator.COUNT;
		}
		if(operatorSign.equalsIgnoreCase(AggregateOperator.MIN.getName())) {
			operator = AggregateOperator.MIN;
		}
		if(operatorSign.equalsIgnoreCase(AggregateOperator.MAX.getName())) {
			operator = AggregateOperator.MAX;
		}
		if(operatorSign.equalsIgnoreCase(AggregateOperator.SUM.getName())) {
			operator = AggregateOperator.SUM;
		}
		
		String[] values = value.split("\\.");
		
		AttributeVariable attr_variable = null;
		if(values.length == 1) {
			attr_variable = generateAttributeVariable(values[0], Constants.GROUPING_ZERO, dic);
		}
		if(values.length == 2) {
			attr_variable = generateAttributeVariable(values[1], values[0], dic);
		}
		
		AggregateExpression expression = new AggregateExpression(operator, attr_variable);
		return expression;
	}
	
	private static  SimpleExpression processSimpleExpression(String express, HashMap<String, GroupingVaribale> dic) {
		String[] values = express.split("\\.");
		AttributeVariable attr_variable = null;
		if(values.length == 1) {
			attr_variable = generateAttributeVariable(values[0], Constants.GROUPING_ZERO, dic);
		}
		if(values.length == 2) {
			attr_variable = generateAttributeVariable(values[1], values[0], dic);
		}
		SimpleExpression expression = new SimpleExpression(attr_variable);
		return expression;
	}
	
	private static AttributeVariable generateAttributeVariable(String name, String belongTo, HashMap<String, GroupingVaribale> dic) {
		GroupingVaribale g_variable = null;
		if(dic.get(belongTo.trim()) != null) {
			g_variable = dic.get(belongTo);
		} else {
			g_variable = new GroupingVaribale(belongTo, String.valueOf(dic.size()));
			dic.put(belongTo, g_variable);
		}
		
		AttributeVariable attr_var = new AttributeVariable(g_variable, name);
		return attr_var;
	}
	
	
	/**
	 * This FUNCTION is used to Generate Condition Expression
	 */
	private static Expression generateComputeOrComparisonExpression(String sql, HashMap<String, GroupingVaribale> dic) {
		Pattern pattern = Pattern.compile("\\band\\b|\\bor\\b", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sql);
		
		int start = 0;
		int end = 0;
		Expression previous = null;
		String operator_sign = null;
		String prev_sign = null;
		while(matcher.find()) { //it's a multiple expression e.g year = 1997 and month = 2
			end = matcher.start();
			if(operator_sign != null) {
				prev_sign = operator_sign;
			}
			operator_sign = sql.substring(matcher.start(), matcher.end());
			ComparisonAndComputeExpression current = gernerateComparisonAndComputeExpression(sql.substring(start, end), dic);
			
			start = matcher.end();
			if(prev_sign == null || prev_sign.toLowerCase().equals(ComparisonAndComputeOperator.AND.getSymbol())) {
				previous = compositeComparisonAndComputeExpression(previous, current, ComparisonAndComputeOperator.AND);
			} else if(prev_sign == null || prev_sign.toLowerCase().equals(ComparisonAndComputeOperator.OR.getSymbol())) {
				previous = compositeComparisonAndComputeExpression(previous, current, ComparisonAndComputeOperator.OR);
			}
			if(previous == null) {
				previous = current;
			}
		}
		if( start != 0) {
			ComparisonAndComputeExpression current = gernerateComparisonAndComputeExpression(sql.substring(start, sql.length()), dic);
			if(operator_sign.toLowerCase().equals(ComparisonAndComputeOperator.AND.getSymbol())) {
				previous = compositeComparisonAndComputeExpression(previous, current, ComparisonAndComputeOperator.AND);
			}
			if(operator_sign.toLowerCase().equals(ComparisonAndComputeOperator.OR.getSymbol())) {
				previous = compositeComparisonAndComputeExpression(previous, current, ComparisonAndComputeOperator.OR);
			}
		} else { // it's a single expression e.g year = 1997
			previous =  gernerateComparisonAndComputeExpression(sql, dic);
		}
		
		return previous;
	}
	
	private static ComparisonAndComputeExpression compositeComparisonAndComputeExpression(Expression left, Expression right, ComparisonAndComputeOperator operator) {
		if(left == null || right == null) {
			return null;
		} else {
			return new ComparisonAndComputeExpression(operator, left, right);
		}
	}
	
	private static ComparisonAndComputeExpression gernerateComparisonAndComputeExpression(String sql, HashMap<String, GroupingVaribale> dic) {
		String regex = ">=|<=|<>|>|<|=";
		
		ComparisonAndComputeOperator operator = ComparisonAndComputeOperator.GREATER;
		if(sql.contains(">=")) {
			operator = ComparisonAndComputeOperator.GREATER_EQUAL;
		} else if(sql.contains("<=")) {
			operator = ComparisonAndComputeOperator.LESS_EQUAL;
		} else if(sql.contains("<>")) {
			operator = ComparisonAndComputeOperator.NOT_EQUAL;
		} else if(sql.contains("<")) {
			operator = ComparisonAndComputeOperator.LESS;
		} else if(sql.contains(">")) {
			operator = ComparisonAndComputeOperator.GREATER;
		} else if(sql.contains("=")) {
			operator = ComparisonAndComputeOperator.EQUAL;
		}
		
		String[] strings = sql.split(regex);
		Expression left = generateSingleComputeExpression(strings[0], dic);
		Expression right = generateSingleComputeExpression(strings[1], dic);
	 
		return compositeComparisonAndComputeExpression(left, right, operator);
	}
	
	private static Expression generateSingleComputeExpression(String sql, HashMap<String, GroupingVaribale> dic) {
		String compute_regex = "\\+|-|\\*|/";
		String[] strings = sql.split(compute_regex);
		if(strings.length == 1) {
			return processExpression(sql, dic);
		}
		List<Character> list_sign = new ArrayList<Character>();
		List<Object> list_variable = new ArrayList<Object>();
		for(int i = 0; i < sql.length(); i++) {
			if(sql.charAt(i) == '+' || sql.charAt(i) == '-' || sql.charAt(i) == '*' || sql.charAt(i) == '/') {
				list_sign.add(sql.charAt(i));
			}
		}
		for(int i = 0; i < strings.length; i++) {
			list_variable.add(strings[i]);
		}
		for(int i = 0; i < list_sign.size(); i++) {
			Object tmp = null;
			if(list_sign.get(i) == '*' || list_sign.get(i) == '/') {
				tmp = generateFinalComputeExpression(list_variable.get(i), list_variable.get(i+1) ,list_sign.get(i) , dic);
				list_variable.remove(i);
				list_variable.remove(i);
				list_variable.add(i, tmp);
				
				list_sign.remove(i);
				i = 0;
			}
			
		}
		for(int i = 0, j = 0; i < list_sign.size(); ) {
			Object tmp = null;
			j = i + 1;
			if(list_sign.get(i) == '+' || list_sign.get(i) == '-') {
				tmp = generateFinalComputeExpression(list_variable.get(i), list_variable.get(i+1) ,list_sign.get(i) , dic);
				list_variable.remove(i);
				list_variable.remove(i);
				list_variable.add(i, tmp);
				
				list_sign.remove(i);
				j = 0;
			}
			i = j;
		}
		Expression final_express = (Expression)list_variable.get(0);
		return final_express;
	}
	
	/**
	 * Used to Generate The Final Output of Expression
	 */
	private static Expression generateFinalComputeExpression(Object left_fragment, Object right_fragment, char operator_sign ,HashMap<String, GroupingVaribale> dic) {
		Expression final_result = null;
		ComparisonAndComputeOperator operator = null;
		if(operator_sign == '+') {
			operator = ComparisonAndComputeOperator.ADDITION;
		}
		if(operator_sign == '-') {
			operator = ComparisonAndComputeOperator.MINUS;
		}
		if(operator_sign == '*') {
			operator = ComparisonAndComputeOperator.MULTIPLICATION;
		}
		if(operator_sign == '/') {
			operator = ComparisonAndComputeOperator.DIVID;
		}
			
		
		if(left_fragment instanceof String && right_fragment instanceof Expression) {
			final_result = generateFinalComputeExpression((String)left_fragment, (Expression) right_fragment, operator, dic);
		}
		if(left_fragment instanceof String && right_fragment instanceof String) {
			final_result = generateFinalComputeExpression((String)left_fragment, (String) right_fragment, operator, dic);
		}
		if(left_fragment instanceof Expression && right_fragment instanceof String) {
			final_result = generateFinalComputeExpression((Expression)left_fragment, (String) right_fragment, operator, dic);
		}
		if(left_fragment instanceof Expression && right_fragment instanceof Expression) {
			final_result = generateFinalComputeExpression((Expression)left_fragment, (Expression) right_fragment, operator, dic);
		}
		return final_result;
	}
	
	private static Expression generateFinalComputeExpression(String left_fragment, String right_fragment, ComparisonAndComputeOperator operator, HashMap<String, GroupingVaribale> dic) {
		if(StringBuilder.isNumber(left_fragment) && StringBuilder.isNumber(right_fragment)) {
			int value = 0;
			if(operator.equals(ComparisonAndComputeOperator.ADDITION)) {
				value = StringBuilder.toInt(left_fragment) + StringBuilder.toInt(right_fragment);
			}
			if(operator.equals(ComparisonAndComputeOperator.MINUS)) {
				value = StringBuilder.toInt(left_fragment) - StringBuilder.toInt(right_fragment);
			}
			if(operator.equals(ComparisonAndComputeOperator.MULTIPLICATION)) {
				value = StringBuilder.toInt(left_fragment) * StringBuilder.toInt(right_fragment);
			}
			if(operator.equals(ComparisonAndComputeOperator.DIVID)) {
				value = StringBuilder.toInt(left_fragment) / StringBuilder.toInt(right_fragment);
			}
			return new IntegerExpression(value);
		} else {
			if(StringBuilder.isNumber(left_fragment)) {
				return generateFinalComputeExpression(new IntegerExpression(StringBuilder.toInt(left_fragment)), right_fragment, operator, dic);
			}
			if(StringBuilder.isNumber(right_fragment)) {
				return generateFinalComputeExpression(left_fragment, new IntegerExpression(StringBuilder.toInt(right_fragment)), operator, dic);
			}
		}
		Expression left = processExpression(left_fragment, dic);
		Expression right = processExpression(right_fragment, dic);
		return compositeComparisonAndComputeExpression(left, right, operator);
	}
	
	private static Expression generateFinalComputeExpression(Expression left_fragment, String right_fragment, ComparisonAndComputeOperator operator, HashMap<String, GroupingVaribale> dic) {
		Expression right = null;
		if(StringBuilder.isNumber(right_fragment)) {
			right = new IntegerExpression(StringBuilder.toInt(right_fragment));
			if(left_fragment instanceof IntegerExpression) {
				((IntegerExpression)left_fragment).compute((IntegerExpression)right, operator);
				return left_fragment;
			}
		} else {
			right = processExpression(right_fragment, dic);
		}
		return compositeComparisonAndComputeExpression(left_fragment, right, operator);
	}
	
	private static Expression generateFinalComputeExpression(String left_fragment, Expression right_fragment, ComparisonAndComputeOperator operator, HashMap<String, GroupingVaribale> dic) {
		
		Expression left = null;
		if(StringBuilder.isNumber(left_fragment)) {
			left = new IntegerExpression(StringBuilder.toInt(left_fragment));
			if(right_fragment instanceof IntegerExpression) {
				((IntegerExpression)left).compute((IntegerExpression)right_fragment, operator);
				return left;
			}
		} else {
			left = processExpression(left_fragment, dic);
		}
		return compositeComparisonAndComputeExpression(left,  right_fragment, operator);
	}
	
	private static Expression generateFinalComputeExpression(Expression left_fragment, Expression right_fragment, ComparisonAndComputeOperator operator, HashMap<String, GroupingVaribale> dic) {
		return compositeComparisonAndComputeExpression(left_fragment,  right_fragment, operator);
	}
	
}
