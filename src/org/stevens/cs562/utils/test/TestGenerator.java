package org.stevens.cs562.utils.test;

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

public class TestGenerator {
	
	public static String s = " SELECT ty ,zy,  xz,th "
			+ "from sales C, sales D "
			+ "where C.X='1' "
			+ "group by C.X, D.Y : X, Y "
			+ "suchthat X.start_date < '192/1687/200  and Y.start_data > '899222' "
			+ "having count(X.startdate) > 5 ;";
	
	public static void main(String[] strings) throws SQLException {
		
		SqlSentence sqlsetence = new SqlSentence(s);
		GroupByElement gbelement = sqlsetence.getGroupByElement();
		SuchThatElement suchTaht = sqlsetence.getSuchThatElement();
		Collection<GroupingVaribale> gvs = new HashSet<GroupingVaribale>();
		Collection<AttributeVariable> attrs = new HashSet<AttributeVariable>();
		
		GroupingVaribale x_gp = new GroupingVaribale("X");
		AttributeVariable attr_sales_x = new AttributeVariable(x_gp, "sales"); //X.sales
		
		GroupingVaribale y_gp = new GroupingVaribale("Y");
		AttributeVariable attr_sales_y = new AttributeVariable(y_gp, "sales"); //Y.sales
		
		
		gvs.add(new GroupingVaribale("self"));
		gvs.add(x_gp);
		gvs.add(y_gp);
		
		
		attrs.add(attr_sales_x);
		attrs.add(attr_sales_y);
		
		gbelement.setGrouping_variables(gvs);
		gbelement.setGrouping_attributes(attrs);
		// Min(Y.sales)
		Expression y_sales_average = new AggregateExpression(AggregateOperator.AVERAGE, attr_sales_y);
		
		//X.sales > Min(Y.sales)
		Expression x_sales_gt_y_min_sales = new ComparisonAndComputeExpression(ComparisonAndComputeOperator.GREATER,new SimpleExpression(attr_sales_x), y_sales_average);
		
		suchTaht.getSuch_that_expressions().add(x_sales_gt_y_min_sales);
		
		AggregateExpressionVisitorImpl express_visitor = new AggregateExpressionVisitorImpl();
		x_sales_gt_y_min_sales.accept(express_visitor);
		
		RelationBuilderVisitor rebuild_visitor = new RelationBuilderVisitor();
		x_sales_gt_y_min_sales.accept(rebuild_visitor);
		Variable x_t = rebuild_visitor.getPairs().get(0).getLeft().getVariable();
		Variable z = x_t;
		
		RelationBuilder builder = new RelationBuilder(sqlsetence);
		builder.build();
		
		//select X.cust, cust, min(quant)
		//from ............
		
		
		SelectElement selectElement = sqlsetence.getSelectElement();
		
		//CUST
		AttributeVariable variable  = new AttributeVariable(new NullVariable(), "cust");
		Expression exp1 = new SimpleExpression(variable);
		//x.cust
		AttributeVariable variable2  = new AttributeVariable(new GroupingVaribale("x"), "cust");
		Expression exp2 = new SimpleExpression(variable2);
		//min(quant)
		AttributeVariable variable3  = new AttributeVariable(new NullVariable(), "quant");
		Expression exp3 = new AggregateExpression(AggregateOperator.MIN, variable3);
		
		AttributeVariable variable4  = new AttributeVariable(new GroupingVaribale("Y"), "sales");
		Expression exp4 = new AggregateExpression(AggregateOperator.AVERAGE, variable4);
		
		selectElement.getProjectItems().add(exp1);
		selectElement.getProjectItems().add(exp2);
		selectElement.getProjectItems().add(exp3);
		selectElement.getProjectItems().add(exp4);
		
		Connection connection = getConnection();
		CodeGenerator generate = new CodeGenerator(sqlsetence, connection);
		generate.generate();
		connection.close();
		
		/*
		 * dynamic 
		 */
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
