package org.stevens.cs562.sql;

import org.stevens.cs562.sql.visit.Visit;

public interface Expression extends Visit{
	
	/**
	 * caculate
	 */
	void caculate();
	
	Variable getVariable();

}
