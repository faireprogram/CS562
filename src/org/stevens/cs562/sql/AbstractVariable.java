package org.stevens.cs562.sql;

import java.util.Collection;


/**
 * AbstractVariable
 * @author faire_000
 *
 */
public abstract class AbstractVariable implements Variable{

	/**
	 * variable_name
	 */
	String variable_name = null;
	
	/**
	 * variable_alias
	 */
	String variable_alias;
	
	public String getAlias() {
		if(hasAlias()) {
			return variable_alias;
		} else {
			return null;
		}
	}

	public Boolean hasAlias() {
		if(variable_name != null) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

}
