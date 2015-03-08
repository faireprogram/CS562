package org.stevens.cs562.code;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.sqlimpl.SelectElement;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;
import org.stevens.cs562.sql.sqlimpl.SqlSentence;
import org.stevens.cs562.sql.sqlimpl.SuchThatElement;
import org.stevens.cs562.sql.visit.AggregateExpressionVisitorImpl;
import org.stevens.cs562.sql.visit.Visitor;
import org.stevens.cs562.utils.Constants;
import org.stevens.cs562.utils.test.sale_rec_ind;

public class CodeGenerator {

	/**
	 * sentence
	 */
	SqlSentence sqlsentence;
	
	private String usr = "postgres";
	private String psw = "zw198787";
	private String url = "jdbc:postgresql://localhost:5432/test";
	/**
	 * 
	 */
	Connection connection;
	
	public CodeGenerator(SqlSentence sqlsentence, Connection connect) {
		this.sqlsentence = sqlsentence;
		this.connection = connect;
	}

	public void generate() {
		String path = Constants.OUT_PUT_PATH;
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

		SelectElement selectElement = sqlsentence.getSelectElement();
		SuchThatElement suchThatElement = sqlsentence.getSuchThatElement();
		AggregateExpressionVisitorImpl visitor = new AggregateExpressionVisitorImpl();
		for(Expression exp : suchThatElement.getSuch_that_expressions()) {
			visitor.visit(exp);
		}
		
		String str = "public class " + Constants.GENERATE_CODE_MF_TABLE +" {\n";
		// SelectElement
		for(Expression projectItem : selectElement.getProjectItems()) {
			String columName = projectItem.getVariable().getName();
			String type = Constants.INTERGER_TYPE;
			if(projectItem instanceof SimpleExpression) {
				type = find_type(columName, connection);
			}
			columName = projectItem.getConvertionName();
			str += "\t" + type + " " + columName + ";\n";
		}
		//SuchThat Element
		for(Expression suchthat : visitor.getAggregate_expression()) {
			if(!isIn(suchthat, selectElement.getProjectItems())) {
				String columName = suchthat.getConvertionName();
				String type = Constants.INTERGER_TYPE;
				str += "\t" + type + " " + columName + ";\n";
			}
		}
		
		str += "}\n";
		//System.out.println(str);
		file.write(str.getBytes(), 0, str.length());
		file.flush();
		file.close();
	}
	
	private boolean isIn(Expression exp1, Collection<Expression> expCollection) {
		for(Expression exp : expCollection) {
			if(exp.equals(exp1)) {
				return true;
			}
		}
		return false;
	}
	
	private String find_type(String column, Connection connection) throws SQLException {
		String search_type = String.format(Constants.SEARCHING_TYPE, "'sales'", "'" + column + "'");
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(search_type);
		rs.next();
		if(rs.getString(1).toLowerCase().contains("char")) {
			return Constants.STRING_TYPE;
		} 
		if(rs.getString(1).toLowerCase().contains("int")) {
			return Constants.INTERGER_TYPE;
		}
		return Constants.STRING_TYPE;
		
	}
	
	
	/**
	 * generateMF_Main
	 * @param file
	 * @throws IOException
	 */
	private void generateMF_Main(FileOutputStream file) throws IOException {
		List<String> heads_prints = getPrintedHeader();
		String str = "";
		str += "import java.sql.Connection;\n";
		str += "import java.sql.DriverManager;\n";
		str += "import java.sql.ResultSet;\n";
		str += "import java.sql.SQLException;\n";
		str += "import java.sql.Statement;\n\n";
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
		str +=			"\t\tSystem.out.println(\"" + heads_prints.get(1) + "\");\n";
		str +=  		"\t\tSystem.out.println(\"Hello world!!!\");\n";
		str += "\t}\n";
		str += "}\n";
		file.write(str.getBytes(), 0, str.length());
		System.out.println(str);
		file.flush();
		file.close();
	}
	
	private List<String> getPrintedHeader() {
		List<String> strs = new ArrayList<String>();
		SelectElement selectElement = sqlsentence.getSelectElement();
		String up_colum_strs = "";
		String down_seperator_strs = "";
		
		for(Expression projectItem : selectElement.getProjectItems()) {
			String colum = projectItem.getConvertionName().toUpperCase();
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
	
}
