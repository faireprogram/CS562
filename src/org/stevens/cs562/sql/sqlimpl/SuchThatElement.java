package org.stevens.cs562.sql.sqlimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.stevens.cs562.sql.AbstractSqlElement;
import org.stevens.cs562.sql.AggregateOperator;
import org.stevens.cs562.sql.ComparisonAndComputeOperator;
import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.Variable;


/**
 * 
 * @author faire_000
 *
 */
public class SuchThatElement extends AbstractSqlElement{

	/**
	 * groupping_variable
	 */
	private Collection<? extends Variable> groupping_variables = new ArrayList<GroupingVaribale>();
	
	private List<Expression> such_that_expressions = new ArrayList<Expression>();
	
//	private List<>
	
//	private Collection
	public SuchThatElement(String elementSql) {
		super(elementSql);
	}

	/* (non-Javadoc)
	 * GET A BUNCH OF Expression describe the relationship between them
	 */
	@Override
	protected void convert(String elementSql) {
		
		// X.sales > Min(Y.sales)
		
		GroupingVaribale x_gp = new GroupingVaribale("X");
		AttributeVariable attr_sales_x = new AttributeVariable(x_gp, "sales"); //X.sales
		
		GroupingVaribale y_gp = new GroupingVaribale("Y");
		AttributeVariable attr_sales_y = new AttributeVariable(y_gp, "sales"); //Y.sales
		
		// Min(Y.sales)
		Expression y_sales_average = new AggregateExpression(AggregateOperator.AVERAGE, attr_sales_y);
		
		//X.sales > Min(Y.sales)
		Expression x_sales_gt_y_min_sales = new ComparisonAndComputeExpression(ComparisonAndComputeOperator.GREATER,new SimpleExpression(attr_sales_x), y_sales_average);
		
		this.such_that_expressions.add(x_sales_gt_y_min_sales);
		
		
	}
	
	
	
	
//	private void 
	

}
