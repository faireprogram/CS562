package org.stevens.cs562.sql.visit;

import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.IntegerExpression;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;

public abstract class AbstractVisitor implements Visitor  {

	public void visit(Variable variable) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Expression expression) {
		if(expression instanceof SimpleExpression) {
			visit((SimpleExpression)expression);
		}
		if(expression instanceof ComparisonAndComputeExpression) {
			visit((ComparisonAndComputeExpression)expression);
		}
		if(expression instanceof AggregateExpression) {
			visit((AggregateExpression)expression);
		}
		if(expression instanceof IntegerExpression) {
			visit((IntegerExpression)expression);
		}
	}

	public void visit(SimpleExpression expression) {
		System.out.println("Visit SimpleExpression");
	}

	public void visit(ComparisonAndComputeExpression expression) {
		
		expression.getLeft().accept(this);
		expression.getRight().accept(this);
		System.out.println("Visit ComparisonAndComputeExpression");
	}

	public void visit(AggregateExpression expression) {
		System.out.println("Visit AggregateExpression");
	}
	
	public void visit(IntegerExpression expression) {
		System.out.println("Visit IntegerExpression");
	}

	
}
