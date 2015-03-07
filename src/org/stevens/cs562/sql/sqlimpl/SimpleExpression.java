package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.AbstractExpression;
import org.stevens.cs562.sql.Variable;

public class SimpleExpression extends AbstractExpression{

	/**
	 * attribute
	 */
	private Variable attribute;

	/**
	 * @param attribute
	 */
	public SimpleExpression(Variable attribute) {
		super();
		this.attribute = attribute;
	}

	/**
	 * @return the attribute
	 */
	public Variable getAttribute() {
		return attribute;
	}
	
	
	
	
}
