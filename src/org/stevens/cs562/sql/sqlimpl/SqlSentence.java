package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.utils.SQLStringParsers;

public class SqlSentence {

	/**
	 * selectElement
	 */
	private SelectElement selectElement;
	
	/**
	 * fromElement
	 */
	private FromElement fromElement;
	
	/**
	 * whereElement
	 */
	private WhereElement whereElement;
	
	/**
	 * groupByElement
	 */
	private GroupByElement groupByElement;
	
	/**
	 * havingElement
	 */
	private HavingElement havingElement;
	
	/**
	 * suchThatElement
	 */
	private SuchThatElement suchThatElement;
	
	

	public SqlSentence(String sql) {
		String[] tmp = SQLStringParsers.parseString(sql);
		selectElement = new SelectElement(tmp[0]);
		fromElement = new FromElement(tmp[1]);
		whereElement = new WhereElement(tmp[2]);
		groupByElement = new GroupByElement(tmp[3]);
		suchThatElement = new SuchThatElement(tmp[4]);
		havingElement = new HavingElement(tmp[5]);
	}

	/**
	 * @return the selectElement
	 */
	public SelectElement getSelectElement() {
		return selectElement;
	}

	/**
	 * @return the fromElement
	 */
	public FromElement getFromElement() {
		return fromElement;
	}

	/**
	 * @return the whereElement
	 */
	public WhereElement getWhereElement() {
		return whereElement;
	}

	/**
	 * @return the groupByElement
	 */
	public GroupByElement getGroupByElement() {
		return groupByElement;
	}

	/**
	 * @return the havingElement
	 */
	public HavingElement getHavingElement() {
		return havingElement;
	}

	/**
	 * @return the suchThatElement
	 */
	public SuchThatElement getSuchThatElement() {
		return suchThatElement;
	}


	
	
	
	
}
