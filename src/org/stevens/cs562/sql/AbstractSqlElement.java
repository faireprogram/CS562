package org.stevens.cs562.sql;

import org.stevens.cs562.sql.sqlimpl.SqlSentence;

public abstract class AbstractSqlElement implements SqlElement{

	/**
	 * selfSentce
	 */
	private SqlSentence selfSentce;
	
	private boolean exist = true;
	
	public AbstractSqlElement(String elementSql, SqlSentence selfSentce) {
		this.selfSentce = selfSentce;
		convert(elementSql);
	}
	
	protected void convert(String elementSql) {
		//TO DO
	}

	/**
	 * @return the selfSentce
	 */
	public SqlSentence getSelfSentce() {
		return selfSentce;
	}

	/**
	 * @param selfSentce the selfSentce to set
	 */
	public void setSelfSentce(SqlSentence selfSentce) {
		this.selfSentce = selfSentce;
	}

	/**
	 * @return the exist
	 */
	public boolean isExist() {
		return exist;
	}

	/**
	 * @param exist the exist to set
	 */
	public void setExist(boolean exist) {
		this.exist = exist;
	}
	
	
	
	
}
