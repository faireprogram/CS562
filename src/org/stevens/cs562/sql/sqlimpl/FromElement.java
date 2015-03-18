package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.AbstractSqlElement;

public class FromElement extends AbstractSqlElement{

	private String table_name;
	
	public FromElement(String elementSql, SqlSentence sentence) {
		super(elementSql, sentence);
		this.table_name = elementSql;
	}

	/**
	 * @return the table_name 
	 */
	public String getTable_name() {
		return table_name;
	}

	/**
	 * @param table_name the table_name to set
	 */
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return table_name;
	}

	
}
