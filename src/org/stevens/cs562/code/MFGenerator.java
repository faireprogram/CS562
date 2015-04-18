package org.stevens.cs562.code;

import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.sqlimpl.AttributeVariable;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.GroupByElement;
import org.stevens.cs562.sql.sqlimpl.GroupingVaribale;
import org.stevens.cs562.utils.Constants;
import org.stevens.cs562.utils.GeneratorHelper;
import org.stevens.cs562.utils.graph.AdjacentNode;

/**
 * MFGenerator is used to generate the MF STRUTURE CODE
 *
 */
public class MFGenerator extends AbstractCodeGenerator{

	public MFGenerator(String sql) {
		super(sql);
	}
	
	@Override
	protected void generateMF_Main(FileOutputStream file) throws Exception {
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
		str +=			GeneratorHelper.gl("List<MFtable> list = new ArrayList<MFtable>();", 3);
		str +=			GeneratorHelper.BLANK;
		str +=			GeneratorHelper.BLANK;
		//------------- Where Such Part
		current_step = 0;
		if(sqlsentence.getGroupByElement().isExist()) {
			str += 			generateScan();
		}
		str +=			GeneratorHelper.BLANK;
		//------------- Having Part
		current_step = 1;
		if(sqlsentence.getHavingElement().isExist()) {
			str +=			GeneratorHelper.gc("  Having Part", 3);
			str +=          generateHavingCondition(3);
		}
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
	
	private String generateScan() throws SQLException {
		String str = "";
		for(Collection<AdjacentNode<GroupingVaribale>> shechdule : relationship) {
			List<Expression> schedule_expressions = new ArrayList<Expression>();
			List<GroupingVaribale> shedule_variable = new ArrayList<GroupingVaribale>();
			
			for(AdjacentNode<GroupingVaribale> group_variable : shechdule) {
				Expression ls = this.relationBuilder.getSuchThatBlockExpressionByVariable(group_variable.getValue());
			//	if(ls != null) {
					schedule_expressions.add(ls);
					shedule_variable.add(group_variable.getValue());
			//	}
			}
			
			
			str +=			GeneratorHelper.gl("ResultSet rs" + current_scan +" = stmt.executeQuery(\"SELECT * FROM Sales\");", 3);
			str +=			GeneratorHelper.gc("   SCAN "+ current_scan+ "  =>  " + shechdule.toString(), 3);
			str +=			GeneratorHelper.gl("while (rs"+ current_scan +".next())  {", 3);
			str +=			GeneratorHelper.gl("boolean is_find = false;", 4);
			str +=			GeneratorHelper.gl("int position = 0;", 4);
			str +=			GeneratorHelper.BLANK;
			/*
			 * IF SQL WHERE ELEMENT not Exist
			 */
			if(sqlsentence.getWhereElement().isExist()) {
				str +=			GeneratorHelper.gc("  THIS IS WHERE CONDITION", 4);
				str +=			generateWhereCondition(4);
			}
			/*
			 * IF ONLY GROUP BY ELEMENT
			 */
			
			str +=			GeneratorHelper.gc("  lookup from MF_TABLE", 4);
			str +=			GeneratorHelper.gl("for(int j = 0; j < list.size(); j++) {", 4);
			str +=			GeneratorHelper.ind(5) + funMFtableEqualTupe();
			str +=			GeneratorHelper.gl("position = j;", 6);
			str +=			GeneratorHelper.gl("is_find = true;", 6);
			str +=			GeneratorHelper.gl("break;", 6);
			str +=			GeneratorHelper.gl("}", 5);
			str +=			GeneratorHelper.gl("}", 4);
			str +=			GeneratorHelper.BLANK;
			str +=  		GeneratorHelper.gc("   UPDATE MF_TABLE ", 4);
			str +=			GeneratorHelper.gl("if(is_find) {", 4);
			str +=			GeneratorHelper.gl("//  SuchThat Condition => " + shechdule.toString() , 5);
			for(int z = 0; z < schedule_expressions.size() ; z++) {
			str +=			generateSuchThat_IfExist(shedule_variable.get(z),(ComparisonAndComputeExpression)schedule_expressions.get(z), 4);
			}
			str +=			GeneratorHelper.gl("} else { ", 4);
			str += 			generateMFTable_Header_Assignment(5);
			str +=			GeneratorHelper.gl("//  SuchThat Condition => " + shechdule.toString() , 5);
			for(int z = 0; z < schedule_expressions.size() ; z++) {
			str +=			generateSuchThat_IfNoExist(shedule_variable.get(z),(ComparisonAndComputeExpression)schedule_expressions.get(z),4);
			}
			str += 			GeneratorHelper.ind(5) + "if(flag_update) {\n";
			str += 			GeneratorHelper.ind(6) + "list.add(mf_entry);\n";
			str += 			GeneratorHelper.ind(5) + "}\n";
			str +=			GeneratorHelper.gl("}", 4);
			str +=			GeneratorHelper.gl("}", 3);
			str +=			GeneratorHelper.BLANK;
			str +=			GeneratorHelper.BLANK;
			
			current_scan++;
		}
		
		return str;
	}
	
	
	private String generateMFTable_Header_Assignment(int incent) throws SQLException {
		String mfTable_header = GeneratorHelper.ind(incent) + "MFtable mf_entry = new MFtable();\n";
		for(AttributeVariable variable : sqlsentence.getGroupByElement().getGrouping_attributes()) {
			String fragment = "";
			if(GeneratorHelper.find_type(variable.getName(), connection, sqlsentence).equals(Constants.STRING_TYPE)) {
				fragment = GeneratorHelper.ind(incent) + "mf_entry." +  variable.getName() + " = " + "rs" + current_scan + ".getString(\"" + variable.getName() + "\");\n";
			}
			if(GeneratorHelper.find_type(variable.getName(), connection, sqlsentence).equals(Constants.INTERGER_TYPE)) {
				fragment = GeneratorHelper.ind(incent) + "mf_entry." +  variable.getName() + " = " + "rs" + current_scan + ".getInt(\"" + variable.getName() + "\");\n";
			}
			mfTable_header	+= fragment;
			 
		}
		mfTable_header +=  GeneratorHelper.ind(incent) + "boolean flag_update = false;\n";
		return mfTable_header;
	}
	
	private String funMFtableEqualTupe() throws SQLException {
		GroupByElement element = sqlsentence.getGroupByElement();
		String outputConditions = "";
		Iterator<AttributeVariable> attr_iterator = element.getGrouping_attributes().iterator();
		while(attr_iterator.hasNext()) {
			AttributeVariable current = attr_iterator.next();
			String type = GeneratorHelper.find_type(current.getName(), connection, sqlsentence);
			if(type.equals(Constants.INTERGER_TYPE)) {
				outputConditions += "list.get(j)." + current.getName() + " == rs" + current_scan + ".getInt(\"" + current.getName() + "\") ";
				
			}
			if(type.equals(Constants.STRING_TYPE)) {
				outputConditions += "list.get(j)." + current.getName() + ".equals(rs" + current_scan + ".getString(\"" + current.getName() + "\")) ";
				
			}
			if(attr_iterator.hasNext()) {
				outputConditions += "&& ";
			}
		}
		return "if(" + outputConditions + ") {\n";
	}





}
