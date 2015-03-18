package org.stevens.cs562.sql.sqlimpl;

import java.util.Collection;

import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.visit.Visitor;

public class NullVariable implements Variable{

	public void accept(Visitor visitor) {
	}

	public String getName() {
		return "";
	}

	public String getAlias() {
		return "";
	}

	public Boolean hasAlias() {
		return false;
	}

	public Variable getBelong() {
		return new NullVariable();
	}

	public Collection<? extends Variable> getContain() {
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "";
	}

	public String getConvertionName() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
