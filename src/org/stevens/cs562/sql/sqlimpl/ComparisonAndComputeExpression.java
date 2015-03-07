package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.AbstractExpression;
import org.stevens.cs562.sql.ComparisonAndComputeOperator;
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
	private Variable left;
	
	/**
	 * right
	 */
	private Variable right;
	
	
}
