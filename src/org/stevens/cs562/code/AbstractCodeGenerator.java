package org.stevens.cs562.code;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.stevens.cs562.sql.AggregateOperator;
import org.stevens.cs562.sql.ComparisonAndComputeOperator;
import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.AttributeVariable;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.GroupByElement;
import org.stevens.cs562.sql.sqlimpl.GroupingVaribale;
import org.stevens.cs562.sql.sqlimpl.HavingElement;
import org.stevens.cs562.sql.sqlimpl.IntegerExpression;
import org.stevens.cs562.sql.sqlimpl.SelectElement;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;
import org.stevens.cs562.sql.sqlimpl.SqlSentence;
import org.stevens.cs562.sql.sqlimpl.StringExpression;
import org.stevens.cs562.sql.sqlimpl.WhereElement;
import org.stevens.cs562.sql.visit.AggregateExpressionVisitorImpl;
import org.stevens.cs562.sql.visit.RelationBuilder;
import org.stevens.cs562.utils.Constants;
import org.stevens.cs562.utils.GeneratorHelper;
import org.stevens.cs562.utils.ResourceHelper;
import org.stevens.cs562.utils.StringBuilder;
import org.stevens.cs562.utils.graph.AdjacentNode;

public abstract class AbstractCodeGenerator implements Generator{

	//------------------ generate connection for USR
	protected String usr;
	protected String psw;
	protected String url;
	
	//------------------ generate sqlsentence
	protected SqlSentence sqlsentence;
	
	protected RelationBuilder relationBuilder;
	
	protected Connection connection;
	//----------------------------------------------------------------------------------------
	protected List<Collection<AdjacentNode<GroupingVaribale>>> relationship;
	
	//----------------------------------------------------------------------------------------
	// Global Status Variable
	protected int current_scan = 0;
	protected int current_step = 0; // 0 => where/such step, 1 => having
	
	public AbstractCodeGenerator(String sql) {
		this.sqlsentence = new SqlSentence(sql);
		this.connection = ResourceHelper.getConnection();
		this.usr = ResourceHelper.getValue("usrname");
		this.psw = ResourceHelper.getValue("password");
		this.url = ResourceHelper.getValue("postsql_url");
		this.relationBuilder = new RelationBuilder(sqlsentence);
		this.relationship = this.relationBuilder.build();
	}

	/*
	 * Generate TABLE ------------------------------------------------------------------BEGIN
	 */
	public void generateTable() {
		try {
			FileOutputStream mf_table = new FileOutputStream(getFinal_Path() +getOutPutTableName() + ".java");
			generateMF_Table(mf_table);
			mf_table.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
		GeneratorHelper.getAllAggregateExpression(visitor, sqlsentence);
		
		String str = "public class " + getOutPutTableName() +" {\n";
			str += "\t//------------------------------------------------------------------\n";
			str += "\t// generate grouping attributes automatically\n";
			str += "\t//------------------------------------------------------------------\n";
		
		for(AttributeVariable grouping_attributes : groupByElement.getGrouping_attributes()) {
			String columName = grouping_attributes.getName();
			String type = Constants.INTERGER_TYPE;
			type = GeneratorHelper.find_type(grouping_attributes.getName(), connection, sqlsentence);
			str += "\tpublic " + type + " " + columName + ";\n";
		}
		
		str += "\t//------------------------------------------------------------------\n";
		str += "\t// generate aggregates value automatically\n";
		str += "\t//------------------------------------------------------------------\n";
		HashMap<String, String> records = new HashMap<String, String>();
		for(Expression aggre : visitor.getAggregate_expression()) {
			
				/*
				 * Rebind the Aggregate to the relation of Grouping Variable
				 */
				((GroupingVaribale)aggre.getVariable().getBelong()).getAll_aggregates().add((AggregateExpression)aggre);
				
				String columName = aggre.getVariable().getName();
				if(records.get(aggre.getConvertionName()) != null) {
					continue;
				}
				
				String type = GeneratorHelper.find_type(columName, connection, sqlsentence);
				if(((AggregateExpression)aggre).getOperator().equals(AggregateOperator.AVERAGE)) {
					String[] s = ((AggregateExpression)aggre).getSumCountName().split(",");
					if(records.get(s[0]) == null) {
						str += "\tpublic " + type + " " + s[0] + ";\n";
						records.put(s[0], s[0]);
					}
					if(records.get(s[1]) == null) {
						str += "\tpublic " + type + " " + s[1] + ";\n";
						records.put(s[1], s[0]);
					}
				}
				str += "\tpublic " + type + " " + aggre.getConvertionName() + ";\n";
				records.put(aggre.getConvertionName(), aggre.getConvertionName());
		}

		str += "}\n";
		file.write(str.getBytes(), 0, str.length());
		file.flush();
		file.close();
	}
	
	private String getFinal_Path() {
		return StringBuilder.getFinalPath(ResourceHelper.getValue("output"));
	}
//	
	private String getProperPath() {
		return StringBuilder.getProperPath(ResourceHelper.getValue("output"));
	}
	
	private boolean isGroupingAttribute(Variable a) {
		return this.sqlsentence.isGroupingAttribute(a);
	}
	
	/*
	 * Generate TABLE -------------------------------------------------------------------END
	 */
	
	/*
	 * Generate Main -------------------------------------------------------------------Begin
	 */
	/* (non-Javadoc)
	 * @see org.stevens.cs562.code.Generator#generateMain()
	 */
	public void generateMain() {
		try {
			FileOutputStream mf_main;
			mf_main = new FileOutputStream(getFinal_Path() + getOutPutMainName() + ".java");
			generateMF_Main(mf_main);
			mf_main.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void generateMF_Main(FileOutputStream file) throws Exception {
		// TODO 
	}
	
	/*
	 * Generate Main TOOL FUNCTION-------------------------------------------------------BEGIN
	 */
	//--------------------------------------- Select Part ---------------------------------------
	protected List<String> getPrintedHeader() {
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
	

	//--------------------------------------- Where Part ---------------------------------------
	protected String generateWhereCondition(int incent) {
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

	
	//--------------------------------------- Such That Part ---------------------------------------
	
	protected String updateMFTable_Ifexist(GroupingVaribale current_variable, ComparisonAndComputeExpression current_shedule_expressions, int incent) {
		String mfTable = "";
		/*
		 * Group 0 needn't compute the codition
		 */
		if(current_shedule_expressions != null) {
			mfTable = GeneratorHelper.ind(incent) + "if(";
			String s = generateStringFromCondition(current_shedule_expressions.getLeft(), current_shedule_expressions.getRight(), current_shedule_expressions.getOperator());
			mfTable   += ( s + ") {\n ");
		}
		
		incent++;
		if(current_shedule_expressions == null) { // no condition
			incent--;
		}
		HashMap<String, Boolean> expression_status = new HashMap<String, Boolean>();
		for(AggregateExpression expression : current_variable.getAll_aggregates()) {
			// AVG
			if(expression.getOperator().equals(AggregateOperator.AVERAGE)) {
				String[] strs = expression.getSumCountName().split(",");
				String fragment1 = "";
				String fragment2 = "";
				//sum
				if(expression_status.get(strs[0]) == null) {
					fragment1 = GeneratorHelper.ind(incent) + "list.get(position)." + 
							 strs[0] + " = " + "list.get(position)." + strs[0] + " + rs" + current_scan + ".getInt(\"" + expression.getVariable().getName() + "\");\n";
					expression_status.put(strs[0], true);
				}
				//count
				if(expression_status.get(strs[1]) == null) {
					fragment2 = GeneratorHelper.ind(incent) + "list.get(position)." + 
							 strs[1] + " = " + "list.get(position)." + strs[1] + " + 1;\n";
					expression_status.put(strs[1], true);
				}

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
								 expression.getConvertionName() + " >= rs" + current_scan + ".getInt(\"" + expression.getVariable().getName() + "\")" +
								 " ? " + "list.get(position)." + expression.getConvertionName() + " : rs" + current_scan + ".getInt(\""+ expression.getVariable().getName() +"\");\n";
				mfTable += fragment1;
			}
			//count min
			if(expression.getOperator().equals(AggregateOperator.MIN)) {
				String fragment1 = GeneratorHelper.ind(incent) + "list.get(position)." + 
								 expression.getConvertionName() + " = " + "list.get(position)." + 
								 expression.getConvertionName() + " <= rs" + current_scan + ".getInt(\"" + expression.getVariable().getName() + "\")" +
								 " ? " + "list.get(position)." + expression.getConvertionName() + " : rs" + current_scan + ".getInt(\""+ expression.getVariable().getName() +"\");\n";
				mfTable += fragment1;
			}
			//count count
			if(expression.getOperator().equals(AggregateOperator.COUNT) && expression_status.get(expression.getConvertionName()) == null) {
				//count
				String fragment1 = GeneratorHelper.ind(incent) + "list.get(position)." + 
								 expression.getConvertionName() + " = " + "list.get(position)." + expression.getConvertionName() + " + 1;\n";
				mfTable += fragment1;
				expression_status.put(expression.getConvertionName(), true);
			}
			//count sum
			if(expression.getOperator().equals(AggregateOperator.SUM) && expression_status.get(expression.getConvertionName()) == null) {
				//sum
				String fragment1 = GeneratorHelper.ind(incent) + "list.get(position)." + 
						expression.getConvertionName() + " = " + "list.get(position)." + expression.getConvertionName() + " + rs" + current_scan +".getInt(\"" + expression.getVariable().getName() + "\");\n";
				mfTable += fragment1;
				expression_status.put(expression.getConvertionName(), true);
			}
		} 
		if(current_shedule_expressions != null) {
			mfTable += GeneratorHelper.ind(incent-1) + "}\n";
		}
		return mfTable;
	}
	
	protected String updateMFTable_IfnonExist(GroupingVaribale current_variable, ComparisonAndComputeExpression current_shedule_expressions, int incent) throws SQLException {
		return updateMFTable_IfnonExist(current_variable, current_shedule_expressions, incent, true);
	}
	
	protected String updateMFTable_IfnonExist(GroupingVaribale current_variable, ComparisonAndComputeExpression current_shedule_expressions, int incent, boolean should_add_flag) throws SQLException {
		/*
		 * Grouping Attributes
		 */
		String mfTable = "";
		/*
		 * Group 0 needn't compute the codition
		 */
		if(current_shedule_expressions != null) {
			mfTable = GeneratorHelper.ind(incent) + "if(";
			String s = generateStringFromCondition(current_shedule_expressions.getLeft(), current_shedule_expressions.getRight(), current_shedule_expressions.getOperator());
			mfTable   += ( s + ") {\n ");
		}
		incent++;
		
		if(current_shedule_expressions == null) { // no condition
			incent--;
		}
		
		/*
		 * Aggregate Operator
		 */
		Iterator<AggregateExpression> express_iterator = current_variable.getAll_aggregates().iterator();
		while(express_iterator.hasNext()) {
			AggregateExpression expression = express_iterator.next();
			// AVG
			if(expression.getOperator().equals(AggregateOperator.AVERAGE)) {
				String[] strs = expression.getSumCountName().split(",");
				//sum
				String fragment1 = GeneratorHelper.ind(incent) + "mf_entry." + 
								 strs[0] + " = " + "rs" + current_scan + ".getInt(\"" + expression.getVariable().getName() + "\");\n";
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
								 expression.getConvertionName() + " = " + "rs" + current_scan + ".getInt(\"" + expression.getVariable().getName() + "\")" + ";\n";
				mfTable += fragment1;
			}
			//count min
			if(expression.getOperator().equals(AggregateOperator.MIN)) {
				String fragment1 = GeneratorHelper.ind(incent) + "mf_entry." + 
						 expression.getConvertionName() + " = " + "rs" + current_scan + ".getInt(\"" + expression.getVariable().getName() + "\")" + ";\n";
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
						 expression.getConvertionName() + " = " + "rs" + current_scan + ".getInt(\"" + expression.getVariable().getName() + "\")" + ";\n";
				mfTable += fragment1;
			}
			
			if(!express_iterator.hasNext() && should_add_flag) {
				mfTable +=  GeneratorHelper.ind(incent) + "flag_update = true;\n";
			}
			
		} 
		
		if(current_shedule_expressions != null) {
			mfTable += GeneratorHelper.ind(incent-1) + "}\n";
		}
		return mfTable;
	}
	
	protected String generateSuchThat_IfExist(GroupingVaribale g_variable, ComparisonAndComputeExpression current_expression,int incent) {
		String suchthat_condition = GeneratorHelper.gc("  UPDATE EMF_TABLE FOR " + g_variable, incent + 1);
		String result = updateMFTable_Ifexist(g_variable,current_expression, incent + 1);
		if(StringBuilder.isEmpty(result)) {
			suchthat_condition += GeneratorHelper.gl(Constants.MESSAGE_NO_AGGREGATES + g_variable, incent + 1);
		} else {
			suchthat_condition += result;
		}
		suchthat_condition += GeneratorHelper.BLANK;
		return suchthat_condition;
	}
	
	protected String generateSuchThat_IfNoExist(GroupingVaribale g_variable, ComparisonAndComputeExpression current_expression,int incent) throws SQLException {
		return generateSuchThat_IfNoExist(g_variable, current_expression, incent, true);
	}
	
	protected String generateSuchThat_IfNoExist(GroupingVaribale g_variable, ComparisonAndComputeExpression current_expression,int incent, boolean should_add_flag) throws SQLException {
		String suchthat_condition = GeneratorHelper.gc("  UPDATE EMF_TABLE FOR " + g_variable, incent + 1);
		String result = updateMFTable_IfnonExist(g_variable,current_expression, incent + 1, should_add_flag);
		if(StringBuilder.isEmpty(result)) {
			suchthat_condition += GeneratorHelper.gl(Constants.MESSAGE_NO_AGGREGATES + g_variable, incent + 1);
		} else {
			suchthat_condition += result;
		}
		suchthat_condition += GeneratorHelper.BLANK;
		return suchthat_condition;
	}
	

	//--------------------------------------- Having part ---------------------------------------
	protected String generateHavingCondition(int incent) {
		String result = GeneratorHelper.ind(incent) + "Iterator<"+ getOutPutTableName() +"> result_having = list.iterator();\n";
		result  +=  GeneratorHelper.ind(incent) + "while(result_having.hasNext()) {\n";
		incent++;
		result  +=  GeneratorHelper.ind(incent) +  getOutPutTableName() + " mf_entry = result_having.next();\n";
		result  +=  GeneratorHelper.ind(incent) + "if(!(";
		HavingElement element = sqlsentence.getHavingElement();
		Iterator<Expression> express_iterator = element.getHaving_expressions().iterator();		
		while(express_iterator.hasNext()) {
			ComparisonAndComputeExpression express = (ComparisonAndComputeExpression)express_iterator.next();
			result += generateStringFromCondition(express.getLeft(), express.getRight(), express.getOperator());
		}
		
		result += " )) { \n";
		result += GeneratorHelper.ind(incent+1) + "result_having.remove();\n";
		result += GeneratorHelper.ind(incent) + "}\n";
		result += GeneratorHelper.ind(incent-1) + "}\n";
		return result;
	}
	
	//--------------------------------------- Projection part ---------------------------------------
	protected String getPrintResultCode(int incent) {
		String show_result = GeneratorHelper.ind(incent) + "Iterator<"+  getOutPutTableName() + "> results = list.iterator();\n";
		int next_in = incent + 1;
		show_result += GeneratorHelper.ind(incent) + "while(results.hasNext()) {\n";
		show_result += GeneratorHelper.ind(next_in) + getOutPutTableName() + " next = results.next();\n";
		Iterator<Expression> express_iterator = sqlsentence.getSelectElement().getProjectItems().iterator();
		while(express_iterator.hasNext()) {
			Expression current = express_iterator.next();
			String fragment = GeneratorHelper.ind(next_in) + "System.out.printf(\"%"+ current.toString().length() +"s\","+ getOutPutString(current) +" + \"\\t\");\n";
			if(!express_iterator.hasNext()) {
				fragment = GeneratorHelper.ind(next_in) + "System.out.printf(\"%"+ current.toString().length() +"s\","+ getOutPutString(current) +" + \"\\t\\n\");\n";
			}
			show_result += fragment;
		}
		return show_result + GeneratorHelper.gl("}", incent);
	}
	
	protected String getOutPutString(Expression expression) {
		String final_string = "";
		if(!(expression instanceof ComparisonAndComputeExpression)) {
			final_string =  "next." + expression.getConvertionName();
		} else {
			String left = getOutPutString(((ComparisonAndComputeExpression)expression).getLeft() );
			String right = getOutPutString(((ComparisonAndComputeExpression)expression).getRight() );
			final_string = left + ((ComparisonAndComputeExpression)expression).getOperator().getJava_name() + right;
			if(((ComparisonAndComputeExpression)expression).getOperator().equals(ComparisonAndComputeOperator.DIVID)) {
				final_string = "(float)" + final_string;
			}
		}
		return final_string;
	}
	
	
	
	// ----------------------------------- convert the expression of sql to expression of Java ----------
	protected String generateStringFromCondition(Expression left, Expression right, ComparisonAndComputeOperator operator) {
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
		if(left instanceof SimpleExpression && right instanceof AggregateExpression) {
			final_result = generateStringFromCondition((SimpleExpression)left, (AggregateExpression)right, operator);
		}
		if(left instanceof AggregateExpression && right instanceof SimpleExpression) {
			final_result = generateStringFromCondition((AggregateExpression)left, (SimpleExpression)right, operator);
		}
		if(left instanceof AggregateExpression && right instanceof AggregateExpression) {
			final_result = generateStringFromCondition((AggregateExpression)left, (AggregateExpression)right, operator);
		}
		if(left instanceof AggregateExpression && right instanceof IntegerExpression) {
			final_result = generateStringFromCondition((AggregateExpression)left, (IntegerExpression)right, operator);
		}
		if(left instanceof IntegerExpression && right instanceof AggregateExpression) {
			final_result = generateStringFromCondition((IntegerExpression)left, (AggregateExpression)right, operator);
		}
		if(left instanceof SimpleExpression && right instanceof ComparisonAndComputeExpression) {
			final_result = generateStringFromCondition((SimpleExpression)left, (ComparisonAndComputeExpression)right, operator);
		}
		if(left instanceof ComparisonAndComputeExpression && right instanceof SimpleExpression) {
			final_result = generateStringFromCondition((ComparisonAndComputeExpression)left, (SimpleExpression)right, operator);
		}
		if(left instanceof AggregateExpression && right instanceof ComparisonAndComputeExpression) {
			final_result = generateStringFromCondition((AggregateExpression)left, (ComparisonAndComputeExpression)right, operator);
		}
		if(left instanceof ComparisonAndComputeExpression && right instanceof AggregateExpression) {
			final_result = generateStringFromCondition((ComparisonAndComputeExpression)left, (AggregateExpression)right, operator);
		}
		if(left instanceof ComparisonAndComputeExpression && right instanceof IntegerExpression) {
			final_result = generateStringFromCondition((ComparisonAndComputeExpression)left, (IntegerExpression)right, operator);
		}
		if(left instanceof IntegerExpression && right instanceof ComparisonAndComputeExpression) {
			final_result = generateStringFromCondition((IntegerExpression)left, (ComparisonAndComputeExpression)right, operator);
		}
		if(left instanceof StringExpression && right instanceof AggregateExpression) {
			final_result = generateStringFromCondition((StringExpression)left, (AggregateExpression)right, operator);
		}
		if(left instanceof AggregateExpression && right instanceof StringExpression) {
			final_result = generateStringFromCondition((AggregateExpression)left, (StringExpression)right, operator);
		}
		if(left instanceof StringExpression && right instanceof SimpleExpression) {
			final_result = generateStringFromCondition((StringExpression)left, (SimpleExpression)right, operator);
		}
		if(left instanceof SimpleExpression && right instanceof StringExpression) {
			final_result = generateStringFromCondition((SimpleExpression)left, (StringExpression)right, operator);
		}
		return final_result;
	}
	
	protected String generateStringFromCondition(ComparisonAndComputeExpression left, IntegerExpression right, ComparisonAndComputeOperator operator) {
		
		String fragment = "";
		if(current_step == 1) {
			fragment = "(" + generateStringFromCondition(left.getLeft(), left.getRight(), left.getOperator()) + ") " + operator.getJava_name() + " "  + right.getValue();
		}
		return fragment;
	}
	
	protected String generateStringFromCondition(IntegerExpression left, ComparisonAndComputeExpression right, ComparisonAndComputeOperator operator) {
		
		String fragment = "";
		if(current_step == 1) {
			fragment = left.getValue() + " " + operator.getJava_name() + " ("  +  generateStringFromCondition(right.getLeft(), right.getRight(), right.getOperator()) + ")";
		}
		return fragment;
	}
	
	protected String generateStringFromCondition(SimpleExpression left, StringExpression right, ComparisonAndComputeOperator operator) {
		String fragment =  "rs" + current_scan +".getString(\"" + left.getVariable().getName() +"\").equals(" + right + ")";
		if(current_step == 1) {
			fragment = right + ".equals(" + "mf_entry." + left.getVariable().getName() + ")";
		}
		return fragment;
	}
	protected String generateStringFromCondition(StringExpression left, SimpleExpression right, ComparisonAndComputeOperator operator) {
		String fragment =  "rs" + current_scan +".getString(\"" + right.getVariable().getName() +"\").equals(" + left + ")";
		if(current_step == 1) {
			fragment =  left + ".equals(" + "mf_entry." + right.getVariable().getName() + ")";
		}
		return fragment;
	}
	protected String generateStringFromCondition(StringExpression left, AggregateExpression right, ComparisonAndComputeOperator operator) {
		String fragment = left + ".equals(" + "mf_entry." + right.getConvertionName() + ")";
		return fragment;
	}
	
	protected String generateStringFromCondition(AggregateExpression left, StringExpression right, ComparisonAndComputeOperator operator) {
		String fragment = right + ".equals(" + "mf_entry." + left.getConvertionName() + ")";
		return fragment;
	}
	
	protected String generateStringFromCondition(AggregateExpression left, ComparisonAndComputeExpression right, ComparisonAndComputeOperator operator) {
		String fragment = "list.get(position)." + left.getConvertionName() + " " + operator.getJava_name() + "(" + generateStringFromCondition(right.getLeft(), right.getRight(), right.getOperator()) + ")";
		if(current_step == 1) {
			fragment = "mf_entry." + left.getConvertionName() + " " + operator.getJava_name() + "(" + generateStringFromCondition(right.getLeft(), right.getRight(), right.getOperator()) + ")";
		}
		return fragment;
	}
	
	protected String generateStringFromCondition(ComparisonAndComputeExpression left, AggregateExpression right, ComparisonAndComputeOperator operator) {
		String fragment = "(" + generateStringFromCondition(left.getLeft(), left.getRight(), left.getOperator()) + ")" + " " + operator.getJava_name() + "list.get(position)." + right.getConvertionName();
		if(current_step == 1) {
			fragment = "(" + generateStringFromCondition(left.getLeft(), left.getRight(), left.getOperator()) + ")" + " " + operator.getJava_name() + "mf_entry." + right.getConvertionName();
		}
		return fragment;
	}
	
	protected String generateStringFromCondition(SimpleExpression left, ComparisonAndComputeExpression right, ComparisonAndComputeOperator operator) {
		String fragment = "list.get(position)." + left.getVariable().getName() + " " + operator.getJava_name() + "(" + generateStringFromCondition(right.getLeft(), right.getRight(), right.getOperator()) + ")";
		if(!isGroupingAttribute(left.getVariable())) {
			fragment = "rs" + current_scan +".getInt(\"" + left.getVariable().getName() + "\") " + operator.getJava_name() + "(" + generateStringFromCondition(right.getLeft(), right.getRight(), right.getOperator()) + ")";
		}
		if(current_step == 1) {
			fragment = "rs" + current_scan +".getInt(\"" + left.getVariable().getName() + "\") " + operator.getJava_name() + "(" + generateStringFromCondition(right.getLeft(), right.getRight(), right.getOperator()) + ")";
		}
		return fragment;
	}
	
	protected String generateStringFromCondition(ComparisonAndComputeExpression left, SimpleExpression right, ComparisonAndComputeOperator operator) {
		String fragment = "(" + generateStringFromCondition(left.getLeft(), left.getRight(), left.getOperator()) + ")" + operator.getJava_name() + " list.get(position)." + right.getVariable().getName();
		if(!isGroupingAttribute(right.getVariable())) {
			fragment = "(" + generateStringFromCondition(left.getLeft(), left.getRight(), left.getOperator()) + ")" + operator.getJava_name() + "rs" + current_scan +".getInt(\"" + right.getVariable().getName() + "\") ";
		}
		if(current_step == 1) {
			fragment = "(" + generateStringFromCondition(left.getLeft(), left.getRight(), left.getOperator()) + ")" + operator.getJava_name() + "rs" + current_scan +".getInt(\"" + right.getVariable().getName() + "\") ";
		}
		return fragment;
	}
	
	protected String generateStringFromCondition(AggregateExpression left, IntegerExpression right, ComparisonAndComputeOperator operator) {
		String fragment = "list.get(position)." + left.getConvertionName() + " " + operator.getJava_name() + " " + right.getValue();
		if(current_step == 1) {
			fragment ="mf_entry." + left.getConvertionName() + " " + operator.getJava_name() + " " + right.getValue();
		}
		return fragment;
	}
	
	protected String generateStringFromCondition(AggregateExpression left, AggregateExpression right, ComparisonAndComputeOperator operator) {
		String fragment = "list.get(position)." + left.getConvertionName() + " " + operator.getJava_name() + " " + "list.get(position)." + right.getConvertionName();
		if(current_step == 1) {
			fragment ="mf_entry." + left.getConvertionName() + " " + operator.getJava_name() + " "  + "mf_entry." + right.getConvertionName();
		}
		return fragment;
	}
	
	protected String generateStringFromCondition(IntegerExpression left, AggregateExpression right, ComparisonAndComputeOperator operator) {
		String fragment = left.getValue() + " " + operator.getJava_name() + " list.get(position)." +  right.getConvertionName();
		if(current_step == 1) {
			fragment =left.getValue() + " " + operator.getJava_name() + " " + "mf_entry." + right.getConvertionName();
		}
		return fragment;
	}
	
	protected String generateStringFromCondition(SimpleExpression left, SimpleExpression right, ComparisonAndComputeOperator operator) {
		String fragment = null;
		try {
			String tyString = GeneratorHelper.find_type(left.getVariable().getName(), connection, sqlsentence);
			if(tyString.equals(Constants.STRING_TYPE)) {
				fragment = "rs" + current_scan +".getString(\"" + left.getVariable().getName() +"\").equals(" + "list.get(position)."  + right.getVariable().getName() + ")";
			}
			if(tyString.equals(Constants.INTERGER_TYPE)) { 
				fragment = "rs" + current_scan +".getInt(\"" + left.getVariable().getName() +"\") == " + "list.get(position)."  + right.getVariable().getName();
			}
			if(operator.equals(ComparisonAndComputeOperator.NOT_EQUAL)) {
				fragment = "!(" + fragment + ")";
			}
			if(current_step == 1) {
					fragment = "mf_entry." + left.getVariable().getName() +".equals(" + right.getVariable().getName() + ")";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fragment;
	}
	protected String generateStringFromCondition(SimpleExpression left, AggregateExpression right, ComparisonAndComputeOperator operator) {
		String fragment = "rs" + current_scan +".getInt(\"" + left.getVariable().getName() +"\") " + operator.getJava_name() +
				" list.get(position)." + right.getConvertionName();
		return fragment;
	}
	protected String generateStringFromCondition(AggregateExpression left, SimpleExpression right, ComparisonAndComputeOperator operator) {
		String fragment = "list.get(position)."  + left.getConvertionName() + " " + operator.getJava_name() +
				 " rs" + current_scan +".getInt(\"" + right.getVariable().getName() +"\")";
		return fragment;
	}
	protected String generateStringFromCondition(SimpleExpression left, IntegerExpression right, ComparisonAndComputeOperator operator) {
		String fragment = "rs"+ current_scan +".getInt(\"" + left.getVariable().getName() +"\") " + operator.getJava_name() + " " + right.getValue();
		if(current_step == 1) {
			fragment = "mf_entry." + left.getVariable().getName() +" " + operator.getJava_name()  + right.getValue() ;
		}
		return fragment;
	}
	protected String generateStringFromCondition(ComparisonAndComputeExpression left, ComparisonAndComputeExpression right, ComparisonAndComputeOperator operator) {
		String fragment1 = generateStringFromCondition(left.getLeft(), left.getRight(), left.getOperator());
		String fragment2 = generateStringFromCondition(right.getLeft(), right.getRight(), right.getOperator());
		return "(" + fragment1 + ") " + operator.getJava_name() + " (" + fragment2 + ")";
	}

	
	/*
	 * Generate Main TOOL FUNCTION-------------------------------------------------------END
	 */
	
	protected String getOutPutTableName() {
		return Constants.GENERATE_CODE_MF_TABLE;
	}
	
	protected String getOutPutMainName() {
		return Constants.GENERATE_CODE_MF_MAIN;
	}
	
	protected String getRelationAlgebraName() {
		return Constants.RELATION_ALGEBRA;
	}
	
	/*
	 * Generate RelationAlgebra-------------------------------------------------------
	 */
	public void generateRelationAlgebra() {
		try {
			FileOutputStream algebra = new FileOutputStream(getFinal_Path() +getRelationAlgebraName());
			String algebra_content = this.sqlsentence.getRelationAlgebra();
			algebra.write(algebra_content.getBytes(), 0, algebra_content.length());
			algebra.flush();
			algebra.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/*
	 * Generate POM-------------------------------------------------------
	 */
	public void generatePom() {
		
		try {
			String pom = ResourceHelper.readPOM(getOutPutMainName());
			FileOutputStream pom_stream = new FileOutputStream( getProperPath() +"pom.xml");
			pom_stream.write(pom.getBytes(), 0, pom.length());
			pom_stream.flush();
			pom_stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
