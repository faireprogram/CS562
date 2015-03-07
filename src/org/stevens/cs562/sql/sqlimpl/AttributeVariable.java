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

}
