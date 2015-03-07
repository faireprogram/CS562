package org.stevens.cs562.sql;

import java.util.Collection;

public interface Variable {

	/**
	 * @return
	 */
	String getAlias();
	
	/**
	 * @return
	 */
	Boolean hasAlias();
	
	/**
	 * @return
	 */
	Variable getBelong();
	
	/**
	 * @return
	 */
	Collection<? extends Variable> getContain();
}
