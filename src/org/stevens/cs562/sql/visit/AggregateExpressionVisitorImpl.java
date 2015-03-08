package org.stevens.cs562.sql.visit;

import java.util.ArrayList;
import java.util.List;

import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;

public class AggregateExpressionVisitorImpl extends AbstractVisitor{
	
	private List<AggregateExpression> aggregate_expression = new ArrayList<AggregateExpression>();

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression)
	 */
	@Override
	public void visit(ComparisonAndComputeExpression expression) {
		expression.getLeft().accept(this);
		expression.getRight().accept(this);
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.SimpleExpression)
	 */
	@Override
	public void visit(SimpleExpression expression) {
		
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.AggregateExpression)
	 */
	@Override
	public void visit(AggregateExpression expression) {
		aggregate_expression.add(expression);
	}

	/**
	 * @return the aggregate_expression
	 */
	public List<AggregateExpression> getAggregate_expression() {
		return aggregate_expression;
	}
	
}
