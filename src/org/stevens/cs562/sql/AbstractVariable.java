package org.stevens.cs562.sql;

import java.util.Collection;

import org.stevens.cs562.sql.sqlimpl.NullVariable;
import org.stevens.cs562.sql.visit.Visitor;
import org.stevens.cs562.utils.StringBuilder;


/**
 * AbstractVariable
 * @author faire_000
 *
 */
public abstract class AbstractVariable implements Variable{

	/**
	 * variable_name
	 */
	protected String variable_name;
	
	/**
	 * variable_alias
	 */
	protected String variable_alias;
	
	public String getAlias() {
		if(hasAlias()) {
			return variable_alias;
		} else {
			return null;
		}
	}

	public Boolean hasAlias() {
		if(variable_alias != null) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
	

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.Variable#getName()
	 */
	public String getName() {
		return this.variable_name;
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.Variable#getBelong()
	 */
	public Variable getBelong() {
		return new NullVariable();
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.Variable#getContain()
	 */
	public Collection<? extends Variable> getContain() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.visit.Visit#accept(org.stevens.cs562.sql.visit.Visitor)
	 */
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//if(getBelong().getName())
		if(this.getBelong().getName() != "") {
			return this.getBelong().getName() + "." + getName();
		}
		return  getName();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Variable v = (Variable) obj;
		if(StringBuilder.isEqual(this.getName(), v.getName())) {
			return true;
		}
		return false;
	}
	
	
	
	
}
