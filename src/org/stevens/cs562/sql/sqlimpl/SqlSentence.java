package org.stevens.cs562.sql.sqlimpl;

import java.util.HashMap;

import org.stevens.cs562.utils.Constants;
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

	/**
	 * HashMap
	 */
	private HashMap<String, GroupingVaribale> grouping_variable_dic;

	public SqlSentence(String sql) {
		String[] tmp = SQLStringParsers.parseString(sql);
		selectElement = new SelectElement(tmp[0], this);
		fromElement = new FromElement(tmp[1], this);
		whereElement = new WhereElement(tmp[2], this);
		groupByElement = new GroupByElement(tmp[3], this);
		suchThatElement = new SuchThatElement(tmp[4], this);
		havingElement = new HavingElement(tmp[5], this);
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

	/**
	 * @return the grouping_variable_dic
	 */
	public HashMap<String, GroupingVaribale> getGrouping_variable_dic() {
		if(grouping_variable_dic == null) {
			grouping_variable_dic = new HashMap<String, GroupingVaribale>();
			GroupingVaribale varible_zero = new GroupingVaribale(Constants.GROUPING_ZERO, String.valueOf(0));
			grouping_variable_dic.put(Constants.GROUPING_ZERO, varible_zero);
		}
		return grouping_variable_dic;
	}

}
