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
	
	


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getLeft().hashCode() * 71 + getRight().hashCode() * 31 + getOperator().hashCode() * 5;
	}

	

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.AbstractExpression#getConvertionName()
	 */
	@Override
	public String getConvertionName() {
		String final_string = "";
		String left_part = this.left.toString();
		String right_part = this.right.toString();
		if(left instanceof AggregateExpression || left instanceof ComparisonAndComputeExpression) {
			left_part = this.getLeft().getConvertionName();
		}
		if(right instanceof AggregateExpression || right instanceof ComparisonAndComputeExpression) {
			right_part = this.getRight().getConvertionName();
		}
		final_string = left_part + " " + this.getOperator() + " " + right_part + " ";
		return final_string;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String final_string = "";
		String left_part = this.left.toString();
		String right_part = this.right.toString();
		if(left instanceof AggregateExpression) {
			left_part = this.getLeft().toString();
		}
		if(right instanceof AggregateExpression) {
			right_part = this.getRight().toString();
		}
		final_string = left_part + " " + this.getOperator() + " " + right_part + " ";
		return final_string;
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
