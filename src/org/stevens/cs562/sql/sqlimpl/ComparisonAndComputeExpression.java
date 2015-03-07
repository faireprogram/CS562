package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.AbstractExpression;
import org.stevens.cs562.sql.ComparisonAndComputeOperator;
import org.stevens.cs562.sql.Expression;

/**
 * ConditionExpression
 * @author faire_000
 *
 */
public class ComparisonAndComputeExpression extends AbstractExpression{

	/**
	 * ComparisonAndComputeOperator
	 */
	private ComparisonAndComputeOperator operator;
	
	/**
	 * left
	 */
	private Expression left;
	
	/**
	 * right
	 */
	private Expression right;

	public ComparisonAndComputeExpression(
			ComparisonAndComputeOperator operator, Expression left,
			Expression right) {
		super();
		this.operator = operator;
		this.left = left;
		this.right = right;
	}

	
	/**
	 * @return the operator
	 */
	public ComparisonAndComputeOperator getOperator() {
		return operator;
	}

	/**
	 * @return the left
	 */
	public Expression getLeft() {
		return left;
	}

	/**
	 * @return the right
	 */
	public Expression getRight() {
		return right;
	}
	
	
}
