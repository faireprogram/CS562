package org.stevens.cs562.sql;

public abstract class AbstractSqlElement implements SqlElement{

	public AbstractSqlElement(String elementSql) {
		convert(elementSql);
	}
	
	protected void convert(String elementSql) {
		//TO DO
	}
	
}
