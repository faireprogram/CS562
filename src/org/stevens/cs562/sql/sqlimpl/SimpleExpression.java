package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.AbstractExpression;
import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.utils.Constants;

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
	public Variable getVariable() {
		return attribute;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getVariable().toString();
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.AbstractExpression#getConvertionName()
	 */
	@Override
	public String getConvertionName() {
		if(!(this.getVariable().getBelong().getName().equals(Constants.GROUPING_ZERO))) {
			return (getVariable().getBelong().getName() + "_" + getVariable().getName()).toLowerCase();
		}
		return getVariable().getName().toLowerCase();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SimpleExpression) {
			SimpleExpression exp1 = (SimpleExpression)obj;
			if(exp1.getVariable().equals(getVariable())) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	
}
