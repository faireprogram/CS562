package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.AbstractExpression;
import org.stevens.cs562.sql.ComparisonAndComputeOperator;
import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.Variable;

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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.left.toString() + " " + this.operator.toString() + " " + this.right.toString();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ComparisonAndComputeExpression) {
			ComparisonAndComputeExpression exp = (ComparisonAndComputeExpression)obj;
			if(exp.getLeft().equals(this.getLeft()) && exp.getRight().equals(getRight()) && exp.getOperator().equals(getOperator())) {
				return true;
			}
		}
		return false;
	}

	
	
}
