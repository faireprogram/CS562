package org.stevens.cs562.sql;

import java.util.Collection;

import org.stevens.cs562.sql.visit.Visit;

public interface Variable extends Visit {

	/**
	 * @return
	 */
	String getName();
	
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
