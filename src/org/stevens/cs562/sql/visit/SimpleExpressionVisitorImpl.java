package org.stevens.cs562.sql.visit;

import java.util.HashSet;
import java.util.Set;

import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;

public class SimpleExpressionVisitorImpl extends AbstractVisitor{
	
	private Set<SimpleExpression> simples_expression = new HashSet<SimpleExpression>();

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
		this.getSimples_expression().add(expression);
	}

	/**
	 * @return the simples_expression
	 */
	public Set<SimpleExpression> getSimples_expression() {
		return simples_expression;
	}

	/**
	 * @param simples_expression the simples_expression to set
	 */
	public void setSimples_expression(Set<SimpleExpression> simples_expression) {
		this.simples_expression = simples_expression;
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.AggregateExpression)
	 */

	
}
