package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.AbstractExpression;
import org.stevens.cs562.sql.AggregateOperator;
import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.visit.Visitor;

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
	public Variable getVariable() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setVariable(Variable attributes) {
		this.attributes = attributes;
	}

	public void visit() {
		
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
			return this.operator.getName() + "(" + this.attributes.getBelong().getName() + "." + this.attributes.getName() + ")";
	}
	
	
	
	
}
