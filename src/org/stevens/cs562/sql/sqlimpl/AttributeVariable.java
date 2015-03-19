package org.stevens.cs562.sql.sqlimpl;

import java.util.Collection;

import org.stevens.cs562.sql.AbstractVariable;
import org.stevens.cs562.sql.Variable;

/**
 * @author faire_000
 *
 */
public class AttributeVariable extends AbstractVariable{
	
	/**
	 * belong_to_variable
	 */
	private Variable belong_to_variable;
	
	

	/**
	 * @param belong_to_variable
	 */
	public AttributeVariable(Variable belong_to_variable, String variable_name) {
		super();
		this.belong_to_variable = belong_to_variable;
		this.variable_name = variable_name;
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.Variable#getBelong()
	 */
	public Variable getBelong() {
		return belong_to_variable;
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.Variable#getContain()
	 */
	public Collection<? extends Variable> getContain() {
		return null;
	}

	public void visit() {
		
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.AbstractVariable#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AttributeVariable) {
			AttributeVariable var = (AttributeVariable)obj;
			if(var.getBelong().equals(getBelong()) && var.getName().equals(getName())) {
				return true;
			}
		}
		return false;
	}
	
}
