package org.stevens.cs562.sql.sqlimpl;

import java.util.Collection;

import org.stevens.cs562.sql.AbstractVariable;
import org.stevens.cs562.sql.Variable;

/**
 * @author faire_000
 *
 */
public class GroupingVaribale extends AbstractVariable{

	/**
	 * attributes
	 */
	private Collection<? extends Variable> attributes;

	public Variable getBelong() {
		return null;
	}

	public Collection<? extends Variable> getContain() {
		return attributes;
	}
}
