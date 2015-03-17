package org.stevens.cs562.code;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.stevens.cs562.sql.AggregateOperator;
import org.stevens.cs562.sql.ComparisonAndComputeOperator;
import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.AttributeVariable;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.GroupByElement;
import org.stevens.cs562.sql.sqlimpl.IntegerExpression;
import org.stevens.cs562.sql.sqlimpl.SelectElement;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;
import org.stevens.cs562.sql.sqlimpl.SqlSentence;
import org.stevens.cs562.sql.sqlimpl.WhereElement;
import org.stevens.cs562.sql.visit.AggregateExpressionVisitorImpl;
import org.stevens.cs562.utils.Constants;
import org.stevens.cs562.utils.GeneratorHelper;
import org.stevens.cs562.utils.ResourceHelper;

public class CodeGenerator {

	/**
	 * sentence
	 */
	SqlSentence sqlsentence;
	
//	private String usr = "postgres";
//	private String psw = "zw198787";
//	private String url = "jdbc:postgresql://localhost:5432/test";
	
	private String usr;
	private String psw;
	private String url;
	/**
	 * 
	 */
	Connection connection;
	
	public CodeGenerator(SqlSentence sqlsentence, Connection connect) throws IOException {
		this.sqlsentence = sqlsentence;
		this.connection = connect;
		this.usr = ResourceHelper.getValue("usrname");
		this.psw = ResourceHelper.getValue("password");
		this.url = ResourceHelper.getValue("postsql_url");
	}

	public void generate() throws IOException {
		String path = ResourceHelper.getValue("output");
		try {
			FileOutputStream mf_table = new FileOutputStream(path +Constants.GENERATE_CODE_MF_TABLE + ".java");
			generateMF_Table(mf_table);
			mf_table.close();
			
			FileOutputStream mf_main = new FileOutputStream(path + Constants.GENERATE_CODE_MF_MAIN + ".java");
			generateMF_Main(mf_main);
			mf_table.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		
	}
	
	/**
	 * generateMF_Table
	 * @param file
	 * @throws IOException
	 * @throws SQLException
	 */
	private void generateMF_Table(FileOutputStream file) throws IOException, SQLException {
		/* fetch the element from sqlsentence*/
		GroupByElement groupByElement = sqlsentence.getGroupByElement();
		AggregateExpressionVisitorImpl visitor = new AggregateExpressionVisitorImpl();
		/*Get all aggregateExpression*/
		getAllAggregateExpression(visitor);
		
		String str = "public class " + Constants.GENERATE_CODE_MF_TABLE +" {\n";
			str += "\t//------------------------------------------------------------------\n";
			str += "\t// generate grouping attributes automatically\n";
			str += "\t//------------------------------------------------------------------\n";
		
		for(AttributeVariable grouping_attributes : groupByElement.getGrouping_attributes()) {
			String columName = grouping_attributes.getName();
			String type = Constants.INTERGER_TYPE;
			type = find_type(grouping_attributes.getName(), connection);
			str += "\tpublic " + type + " " + columName + ";\n";
		}
		
		str += "\t//------------------------------------------------------------------\n";
		str += "\t// generate aggregates value automatically\n";
		str += "\t//------------------------------------------------------------------\n";
		for(Expression aggre : visitor.getAggregate_expression()) {
				String columName = aggre.getVariable().getName();
				String type = find_type(columName, connection);
				if(((AggregateExpression)aggre).getOperator().equals(AggregateOperator.AVERAGE)) {
					String[] s = ((AggregateExpression)aggre).getSumCountName().split(",");
					str += "\tpublic " + type + " " + s[0] + ";\n";
					str += "\tpublic " + type + " " + s[1] + ";\n";
				}
				str += "\tpublic " + type + " " + aggre.getConvertionName() + ";\n";
		}

		str += "}\n";
		file.write(str.getBytes(), 0, str.length());
		file.flush();
		file.close();
	}
	
	private String find_type(String column, Connection connection) throws SQLException {
		if(sqlsentence.getAttributes_type().get(column) != null) {
			return sqlsentence.getAttributes_type().get(column);
		}
		String type = Constants.STRING_TYPE;
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
		return type;
		
	}
	
	private void getAllAggregateExpression(AggregateExpressionVisitorImpl visitor ) {
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
		}
	}
	
	
	/**
	 * generateMF_Main
	 * @param file
	 * @throws IOException
	 * @throws SQLException 
	 */
	private void generateMF_Main(FileOutputStream file) throws IOException, SQLException {
		List<String> heads_prints = getPrintedHeader();
		String str = "";
		str += "import java.sql.Connection;\n";
		str += "import java.sql.DriverManager;\n";
		str += "import java.sql.ResultSet;\n";
		str += "import java.sql.SQLException;\n";
		str += "import java.sql.Statement;\n";
		str += "import java.util.ArrayList;\n";
		str += "import java.util.Iterator;\n";
		str += "import java.util.List;\n\n";
		str +=  GeneratorHelper.gc("   THIS IS AUTOMATICALLY GENERATE CODE", 0);
		str += "public class "+ Constants.GENERATE_CODE_MF_MAIN +" {\n";
		str +=  	"\tpublic static void main(String[] args) {\n";
		str += 			"\t\t" + Constants.GENERATE_CODE_MF_TABLE+"[] sri= new " +Constants.GENERATE_CODE_MF_TABLE + "[500];\n";
		str += 			"\t\tfor(int i=0; i<500; i++) {\n";
		str +=			"\t\t\tsri[i] = new "+ Constants.GENERATE_CODE_MF_TABLE +"();\n";
		str += 			"\t\t}\n";
		str += 			"\t\tString usr =\"" + usr +"\";\n";
		str += 			"\t\tString pwd =\"" + psw +"\";\n";
		str += 			"\t\tString url =\"" + url +"\";\n";
		str += 			"\t\ttry {\n";
		str += 			"\t\t\tClass.forName(\"org.postgresql.Driver\");\n";
		str += 			"\t\t} catch(Exception e) {\n";
		str += 			 "\t\t\te.printStackTrace();\n";
		str +=			"\t\t}\n";
		str +=			"\t\t//----------------------------------------------------------------------\n";
		str +=			"\t\t//PRINT TITLE OF THE RESULT\n";
		str +=			"\t\t//----------------------------------------------------------------------\n";
		str +=			"\t\tSystem.out.println(\"" + heads_prints.get(0) + "\");\n";
		str +=			"\t\tSystem.out.println(\"" + heads_prints.get(1) + "\");\n\n";
		str +=			"\t\t//build the connection  and execute the sql statement\n";
		str +=			GeneratorHelper.gl("Connection conn;", 2);
		str +=			GeneratorHelper.gl("try {", 2);
		str +=			GeneratorHelper.gl("conn = DriverManager.getConnection(url, usr, pwd);", 3);
		str +=			GeneratorHelper.gl("Statement stmt = conn.createStatement(); ", 3);
		str +=			GeneratorHelper.gl("ResultSet rs = stmt.executeQuery(\"SELECT * FROM Sales\");", 3);
		str +=			GeneratorHelper.gl("List<MFtable> list = new ArrayList<MFtable>();", 3);
		str +=			GeneratorHelper.gc("   SCAN 1 ", 3);
		str +=			GeneratorHelper.gl("while (rs.next())  {", 3);
		str +=			GeneratorHelper.gl("boolean is_find = false;", 4);
		str +=			GeneratorHelper.gl("int position = 0;", 4);
		str +=			GeneratorHelper.BLANK;
		str +=			GeneratorHelper.gc("  THIS IS WHERE CONDITION", 4);
		str +=			generateWhereCondition(4);
		str +=			GeneratorHelper.gl("for(int j = 0; j < list.size(); j++) {", 4);
		str +=			GeneratorHelper.ind(5) + funMFtableEqualTupe();
		str +=			GeneratorHelper.gl("position = j;", 6);
		str +=			GeneratorHelper.gl("is_find = true;", 6);
		str +=			GeneratorHelper.gl("break;", 6);
		str +=			GeneratorHelper.gl("}", 5);
		str +=			GeneratorHelper.gl("}", 4);
		str +=			GeneratorHelper.BLANK;
		str +=  		GeneratorHelper.gc("   UPDATE MF_TABLE", 4);
		str +=			GeneratorHelper.gl("if(is_find) {", 4);
		str +=			updateMFTable_Ifexist(5);
		str +=			GeneratorHelper.gl("} else { ", 4);
		str += 			updateMFTable_IfnonExist(5);
		str +=			GeneratorHelper.gl("}", 4);
		str +=			GeneratorHelper.gl("}", 3);
		str +=			GeneratorHelper.gc("  This part is used to print the result", 3);
		str +=			getPrintResultCode(3);
		str +=			GeneratorHelper.gl("conn.close();\n", 3);
		str +=			GeneratorHelper.gl("} catch (SQLException e) {", 2);
		str +=			GeneratorHelper.gl("e.printStackTrace();", 3);
		str +=			GeneratorHelper.gl("}", 2);  
		str += "\t}\n";
		str += "}\n";
		file.write(str.getBytes(), 0, str.length());
		System.out.println(str);
		file.flush();
		file.close();
	}
	
	private String funMFtableEqualTupe() throws SQLException {
		GroupByElement element = sqlsentence.getGroupByElement();
		String outputConditions = "";
		Iterator<AttributeVariable> attr_iterator = element.getGrouping_attributes().iterator();
		while(attr_iterator.hasNext()) {
			AttributeVariable current = attr_iterator.next();
			String type = find_type(current.getName(), connection);
			if(type.equals(Constants.INTERGER_TYPE)) {
				outputConditions += "list.get(j)." + current.getName() + " == rs.getInt(\"" + current.getName() + "\") ";
				
			}
			if(type.equals(Constants.STRING_TYPE)) {
				outputConditions += "list.get(j)." + current.getName() + ".equals(rs.getString(\"" + current.getName() + "\")) ";
				
			}
			if(attr_iterator.hasNext()) {
				outputConditions += "&& ";
			}
		}
		return "if(" + outputConditions + ") {\n";
	}
	
	private String updateMFTable_IfnonExist(int incent) throws SQLException {
		String mfTable = GeneratorHelper.ind(incent) + "MFtable mf_entry = new MFtable();\n";
		AggregateExpressionVisitorImpl visitor = new AggregateExpressionVisitorImpl();
		/*Get all aggregateExpression*/
		getAllAggregateExpression(visitor);
		/*
		 * Grouping Attributes
		 */
		for(AttributeVariable variable : sqlsentence.getGroupByElement().getGrouping_attributes()) {
			String fragment = "";
			if(find_type(variable.getName(), connection).equals(Constants.STRING_TYPE)) {
				fragment = GeneratorHelper.ind(incent) + "mf_entry." +  variable.getName() + " = " + "rs.getString(\"" + variable.getName() + "\");\n";
			}
			if(find_type(variable.getName(), connection).equals(Constants.INTERGER_TYPE)) {
				fragment = GeneratorHelper.ind(incent) + "mf_entry." +  variable.getName() + " = " + "rs.getInt(\"" + variable.getName() + "\");\n";
			}
			mfTable	+= fragment;
			 
		}
		
		/*
		 * Aggregate Operator
		 */
		for(AggregateExpression expression : visitor.getAggregate_expression()) {
			// AVG
			if(expression.getOperator().equals(AggregateOperator.AVERAGE)) {
				String[] strs = expression.getSumCountName().split(",");
				//sum
				String fragment1 = GeneratorHelper.ind(incent) + "mf_entry." + 
								 strs[0] + " = " + "rs.getInt(\"" + expression.getVariable().getName() + "\");\n";
				//count
				String fragment2 = GeneratorHelper.ind(incent) + "mf_entry." + 
								 strs[1] + " = 1;\n";
				//sum / count
				String fragment3 = GeneratorHelper.ind(incent) + "mf_entry." + 
								 expression.getConvertionName() + " = " + "mf_entry." + strs[0] + " / " + "mf_entry." + strs[1] + ";\n";
				mfTable += (fragment1 + fragment2 + fragment3);
			} 
			//count max
			if(expression.getOperator().equals(AggregateOperator.MAX)) {
				
				String fragment1 = GeneratorHelper.ind(incent) + "mf_entry." + 
								 expression.getConvertionName() + " = " + "rs.getInt(\"" + expression.getVariable().getName() + "\")" + ";\n";
				mfTable += fragment1;
			}
			//count min
			if(expression.getOperator().equals(AggregateOperator.MIN)) {
				String fragment1 = GeneratorHelper.ind(incent) + "mf_entry." + 
						 expression.getConvertionName() + " = " + "rs.getInt(\"" + expression.getVariable().getName() + "\")" + ";\n";
				mfTable += fragment1;
			}
			//count count
			if(expression.getOperator().equals(AggregateOperator.COUNT)) {
				//count
				String fragment1 = GeneratorHelper.ind(incent) + "mf_entry." + 
								 expression.getConvertionName() + " = " + "1;\n";
				mfTable += fragment1;
			}
			//count sum
			if(expression.getOperator().equals(AggregateOperator.SUM)) {
				//sum
				String fragment1 = GeneratorHelper.ind(incent) + "mf_entry." + 
						 expression.getConvertionName() + " = " + "rs.getInt(\"" + expression.getVariable().getName() + "\")" + ";\n";
				mfTable += fragment1;
			}
		} 
		
		return mfTable + GeneratorHelper.ind(incent) + "list.add(mf_entry);\n";
	}
	
	private String updateMFTable_Ifexist(int incent) {
		String mfTable = "";
		AggregateExpressionVisitorImpl visitor = new AggregateExpressionVisitorImpl();
		/*Get all aggregateExpression*/
		getAllAggregateExpression(visitor);
		
		for(AggregateExpression expression : visitor.getAggregate_expression()) {
			// AVG
			if(expression.getOperator().equals(AggregateOperator.AVERAGE)) {
				String[] strs = expression.getSumCountName().split(",");
				//sum
				String fragment1 = GeneratorHelper.ind(incent) + "list.get(position)." + 
								 strs[0] + " = " + "list.get(position)." + strs[0] + " + rs.getInt(\"" + expression.getVariable().getName() + "\");\n";
				//count
				String fragment2 = GeneratorHelper.ind(incent) + "list.get(position)." + 
								 strs[1] + " = " + "list.get(position)." + strs[1] + " + 1;\n";
				//sum / count
				String fragment3 = GeneratorHelper.ind(incent) + "list.get(position)." +
								   expression.getConvertionName() + " = " + 
								   "list.get(position)." +   strs[0] + " / " + "list.get(position)." + strs[1] + ";\n";
				mfTable += (fragment1 + fragment2 + fragment3);
			} 
			//count max
			if(expression.getOperator().equals(AggregateOperator.MAX)) {
				
				String fragment1 = GeneratorHelper.ind(incent) + "list.get(position)." + 
								 expression.getConvertionName() + " = " + "list.get(position)." + 
								 expression.getConvertionName() + " >= rs.getInt(\"" + expression.getVariable().getName() + "\")" +
								 " ? " + "list.get(position)." + expression.getConvertionName() + " : rs.getInt(\""+ expression.getVariable().getName() +"\");\n";
				mfTable += fragment1;
			}
			//count min
			if(expression.getOperator().equals(AggregateOperator.MIN)) {
				String fragment1 = GeneratorHelper.ind(incent) + "list.get(position)." + 
								 expression.getConvertionName() + " = " + "list.get(position)." + 
								 expression.getConvertionName() + " <= rs.getInt(\"" + expression.getVariable().getName() + "\")" +
								 " ? " + "list.get(position)." + expression.getConvertionName() + " : rs.getInt(\""+ expression.getVariable().getName() +"\");\n";
				mfTable += fragment1;
			}
			//count count
			if(expression.getOperator().equals(AggregateOperator.COUNT)) {
				//count
				String fragment1 = GeneratorHelper.ind(incent) + "list.get(position)." + 
								 expression.getConvertionName() + " = " + "list.get(position)." + expression.getConvertionName() + " + 1;\n";
				mfTable += fragment1;
			}
			//count sum
			if(expression.getOperator().equals(AggregateOperator.SUM)) {
				//sum
				String fragment1 = GeneratorHelper.ind(incent) + "list.get(position)." + 
						expression.getConvertionName() + " = " + "list.get(position)." + expression.getConvertionName() + " + rs.getInt(" + expression.getVariable().getName() + ");\n";
				mfTable += fragment1;
			}
		} 
		
		return mfTable;
	}
	
	private String getPrintResultCode(int incent) {
		String show_result = GeneratorHelper.ind(incent) + "Iterator<MFtable> results = list.iterator();\n";
		int next_in = incent + 1;
		show_result += GeneratorHelper.ind(incent) + "while(results.hasNext()) {\n";
		show_result += GeneratorHelper.ind(next_in) + "MFtable next = results.next();\n";
		Iterator<Expression> express_iterator = sqlsentence.getSelectElement().getProjectItems().iterator();
		while(express_iterator.hasNext()) {
			Expression current = express_iterator.next();
			String fragment = GeneratorHelper.ind(next_in) + "System.out.printf(\"%"+ current.toString().length() +"s\",next."+ current.getConvertionName() +" + \"\\t\");\n";
			if(!express_iterator.hasNext()) {
				fragment = GeneratorHelper.ind(next_in) + "System.out.printf(\"%"+ current.toString().length() +"s\",next."+ current.getConvertionName() +" + \"\\t\\n\");\n";
			}
			show_result += fragment;
		}
		return show_result + GeneratorHelper.gl("}", incent);
	}
	
	private List<String> getPrintedHeader() {
		List<String> strs = new ArrayList<String>();
		SelectElement selectElement = sqlsentence.getSelectElement();
		String up_colum_strs = "";
		String down_seperator_strs = "";
		
		for(Expression projectItem : selectElement.getProjectItems()) {
			String colum = projectItem.toString().toUpperCase();
			up_colum_strs += (colum + "\t");
			for(int i =0; i < colum.length(); i++) {
				down_seperator_strs+= "=";
			}
			down_seperator_strs += "\t";
		}
		strs.add(up_colum_strs);
		strs.add(down_seperator_strs);
		
		return strs;
	}
	
	private String generateWhereCondition(int incent) {
		String result = GeneratorHelper.ind(incent) + "if(!(";
		WhereElement element = sqlsentence.getWhereElement();
		Iterator<Expression> express_iterator = element.getWhere_expressions().iterator();		
		while(express_iterator.hasNext()) {
			ComparisonAndComputeExpression express = (ComparisonAndComputeExpression)express_iterator.next();
			result += generateStringFromCondition(express.getLeft(), express.getRight(), express.getOperator());
		}
		
		result += " )) { \n";
		result += GeneratorHelper.ind(incent+1) + "continue;\n";
		result += GeneratorHelper.ind(incent) + "}\n";
		return result;
	}
	
	
	private String generateStringFromCondition(Expression left, Expression right, ComparisonAndComputeOperator operator) {
		String final_result = "";
		if((left instanceof SimpleExpression && right instanceof IntegerExpression)) {
			final_result = generateStringFromCondition((SimpleExpression)left, (IntegerExpression)right, operator);
		}
		if((right instanceof SimpleExpression && left instanceof IntegerExpression)) {
			final_result = generateStringFromCondition((SimpleExpression)right, (IntegerExpression)left, operator);
		}
		if((left instanceof ComparisonAndComputeExpression && right instanceof ComparisonAndComputeExpression)) {
			final_result = generateStringFromCondition((ComparisonAndComputeExpression)left, (ComparisonAndComputeExpression)right, operator);
		}
		if(left instanceof SimpleExpression && right instanceof SimpleExpression) {
			final_result = generateStringFromCondition((SimpleExpression)left, (SimpleExpression)right, operator);
		}
		return final_result;
	}
	
	private String generateStringFromCondition(SimpleExpression left, SimpleExpression right, ComparisonAndComputeOperator operator) {
		String fragment = "rs.getString(\"" + left.getVariable().getName() +"\").equals(" + right.getVariable().getName() + ")";
		return fragment;
	}
	private String generateStringFromCondition(SimpleExpression left, AggregateExpression right, ComparisonAndComputeOperator operator) {
		return null;
	}
	private String generateStringFromCondition(SimpleExpression left, IntegerExpression right, ComparisonAndComputeOperator operator) {
		String fragment = "rs.getInt(\"" + left.getVariable() +"\") " + operator.getJava_name() + " " + right.getValue();
		return fragment;
	}
	private String generateStringFromCondition(ComparisonAndComputeExpression left, ComparisonAndComputeExpression right, ComparisonAndComputeOperator operator) {
		String fragment1 = generateStringFromCondition(left.getLeft(), left.getRight(), left.getOperator());
		String fragment2 = generateStringFromCondition(right.getLeft(), right.getRight(), right.getOperator());
		return "(" + fragment1 + ") " + operator.getJava_name() + " (" + fragment2 + ")";
	}
}
