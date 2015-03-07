package org.stevens.cs562.utils.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;
import org.stevens.cs562.sql.sqlimpl.SqlSentence;
import org.stevens.cs562.sql.sqlimpl.SuchThatElement;
import org.stevens.cs562.sql.visit.ExpressionVisitorImpl;
import org.stevens.cs562.sql.visit.RelationBuilder;
import org.stevens.cs562.sql.visit.RelationBuilderVisitor;
import org.stevens.cs562.sql.visit.Visitor;
import org.stevens.cs562.utils.graph.AdjacentList;
import org.stevens.cs562.utils.graph.AdjacentNode;
import org.stevens.cs562.utils.graph.AdjacentNodeImpl;
import org.stevens.cs562.utils.graph.TopologicalGraph;

public class Test {
	
	
	public static String s = " SELECT ty ,zy,  xz,th "
			+ "from sales C, sales D "
			+ "where C.X='1' "
			+ "group by C.X, D.Y : X, Y "
			+ "suchthat X.start_date < '192/1687/200  and Y.start_data > '899222' "
			+ "having count(X.startdate) > 5 ;";
	
	private static String readLine(String format) throws IOException {
	    
	    System.out.print(String.format(format, null));
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
	            System.in));
	    return reader.readLine();
	}
	
	/**
	 * 
	 */
	private static void test_topological_sort() {
		AdjacentList<Integer, AdjacentNode<Integer>> list = new AdjacentList<Integer, AdjacentNode<Integer>>();
		AdjacentNode<Integer> A = new AdjacentNodeImpl<Integer>(null,1);
		AdjacentNode<Integer> B = new AdjacentNodeImpl<Integer>(null,2);
		AdjacentNode<Integer> C = new AdjacentNodeImpl<Integer>(null,3);
		AdjacentNode<Integer> D = new AdjacentNodeImpl<Integer>(null,4);
		Collection<AdjacentNode<Integer>> ARelation = new ArrayList<AdjacentNode<Integer>>();
		ARelation.add(B);
		ARelation.add(C);
		A.setEdgeVetex(ARelation);
		B.getEdgeVetex().add(C);
		C.getEdgeVetex().add(B);
		list.add(A);
		list.add(B);
		list.add(C);
		list.add(D);
		
//		list.get(index)
		System.out.println(list.get(0).getValue());
		System.out.println(list.get(1).getValue());
		System.out.println(list.get(2).getValue());
		System.out.println(list.get(3).getValue());
		
		
		TopologicalGraph<Integer> graph = new TopologicalGraph<Integer>(list);
		List<Collection<AdjacentNode<Integer>>> layers = graph.sort();
		System.out.println(layers.size());
	}
	public static void main(String [ ] args) throws IOException {
//		RelationalAnalysis rs = new RelationalAnalysis();
//		SqlSentence ss = new SqlSentence(Test.s);
//		System.out.println("Hello World211d") ; 

		
		// X.sales > Min(Y.sales)
		
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
		
		ExpressionVisitorImpl express_visitor = new ExpressionVisitorImpl();
		x_sales_gt_y_min_sales.accept(express_visitor);
		express_visitor.test();
		
		RelationBuilderVisitor rebuild_visitor = new RelationBuilderVisitor();
		x_sales_gt_y_min_sales.accept(rebuild_visitor);
		Variable x_t = rebuild_visitor.getPairs().get(0).getLeft().getVariable();
		Variable z = x_t;
		
		RelationBuilder builder = new RelationBuilder(sqlsetence);
		builder.build();
		
	}
}
