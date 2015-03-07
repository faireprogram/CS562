package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.AbstractExpression;
import org.stevens.cs562.sql.AggregateOperator;
import org.stevens.cs562.sql.Variable;

/**
 * @author faire_000
 *
 */
public class AggregateExpression extends AbstractExpression{

	/**
	 * AggregateOperator
	 */
	private AggregateOperator operator;
	
	/**
	 * attributes
	 */
	private Variable attributes;

	public AggregateExpression(AggregateOperator operator, Variable attributes) {
		super();
		this.operator = operator;
		this.attributes = attributes;
	}

	/**
	 * @return the operator
	 */
	public AggregateOperator getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(AggregateOperator operator) {
		this.operator = operator;
	}

	/**
	 * @return the attributes
	 */
	public Variable getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Variable attributes) {
		this.attributes = attributes;
	}
	
	
	
	
}
