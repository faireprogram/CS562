package org.stevens.cs562.utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.stevens.cs562.sql.AggregateOperator;
import org.stevens.cs562.sql.SqlElement;
import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.AttributeVariable;
import org.stevens.cs562.sql.sqlimpl.GroupingVaribale;
import org.stevens.cs562.sql.sqlimpl.SelectElement;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;

public class SQLStringParsers {
	
	
	/**
	 * 
	 */
	public static String[] parseString(String sql) {
		Pattern p = Pattern.compile(Constants.SQL_REGULAR_EXPRESSION, Pattern.CASE_INSENSITIVE);
		Matcher matchers = p.matcher(sql);
		String[] tmp = new String[6];
		if(matchers.find()) {

			tmp[0] = matchers.group("selectcause");
			tmp[1] = matchers.group("fromcause");
			tmp[2] = matchers.group("wherecause");
			tmp[3] = matchers.group("groupby");
			tmp[4] = matchers.group("suchthat");
			if(matchers.group("having") != null ) {
				tmp[5] = matchers.group("having");
			} else if(matchers.group("having1") != null){
				tmp[5] = matchers.group("having1");
			} else {
				tmp[5] = null;
			}
			
			System.out.println(matchers.group("selectcause"));
			System.out.println(matchers.group("fromcause"));
			System.out.println(matchers.group("wherecause"));
			System.out.println(matchers.group("groupby"));
			System.out.println(matchers.group("suchthat"));
			System.out.println(matchers.group("having"));
			
		}
		return tmp;
	}
	
	/**
	 * parseStringToSelectElement
	 * @param sql
	 * @return
	 */
	public static void parseStringToSelectElement(String sql, SelectElement element) {
		
		
		String[] strings = sql.split(",");
		for(int i = 0; i < strings.length; i++) {
			strings[i] = strings[i].trim();
			if(strings[i].matches("avg\\(.+?\\)|count\\(.+?\\)|avg\\(.+?\\)|max\\(.+?\\)")) {
				element.getProjectItems().add(processAggregateExpression(strings[i], element.getSelfSentce().getGrouping_variable_dic()));
			} else {
				element.getProjectItems().add(processSimpleExpression(strings[i], element.getSelfSentce().getGrouping_variable_dic()));
			}
		}
		System.out.println(element);
	}
			
	private static AggregateExpression processAggregateExpression(String express, HashMap<String, GroupingVaribale> dic) {
		String value = express.replaceAll("avg\\((.+?)\\)|count\\((.+?)\\)|avg\\((.+?)\\)|max\\((.+?)\\)|sum\\((.+?)\\)", "$1$2$3$4$5").trim();
		
		String operatorSign = express.replaceAll("(avg)\\(.+?\\)|(count)\\(.+?\\)|(min)\\(.+?\\)|(max)\\(.+?\\)|(sum)\\(.+?\\)", "$1$2$3$4$5");

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
			attr_variable = generateAttributeVariable(values[0], null, dic);
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
		if(dic.get(belongTo) != null) {
			g_variable = dic.get(belongTo);
		} else {
			g_variable = new GroupingVaribale(belongTo, String.valueOf(dic.size()));
			dic.put(belongTo, g_variable);
		}
		
		AttributeVariable attr_var = new AttributeVariable(g_variable, name);
		return attr_var;
	}
}
