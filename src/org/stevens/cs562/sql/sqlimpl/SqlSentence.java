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
	
	/**
	 * HashMap
	 */
	private HashMap<String, String> attributes_type;

	public SqlSentence(String sql) {
		String[] tmp = SQLStringParsers.parseString(sql);
		/* THIS SHOULD BE FIRST */
		groupByElement = new GroupByElement(tmp[3], this);
		fromElement = new FromElement(tmp[1], this);
		/* THIS SHOULD BE SECOND */
		selectElement = new SelectElement(tmp[0], this);
		/* THIS SHOULD BE LAST */
		whereElement = new WhereElement(tmp[2], this);
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
	
	

	/**
	 * @return the attributes_type
	 */
	public HashMap<String, String> getAttributes_type() {
		if(attributes_type == null) {
			attributes_type = new HashMap<String, String>();
		}
		return attributes_type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return selectElement.toString() + fromElement.toString() + whereElement.toString() + groupByElement.toString() + suchThatElement.toString() + havingElement.toString();
	}

	
}
