package org.stevens.cs562.sql.sqlimpl;

import java.util.ArrayList;
import java.util.Collection;

import org.stevens.cs562.sql.AbstractSqlElement;
import org.stevens.cs562.sql.Variable;

public class GroupByElement extends AbstractSqlElement {

	/**
	 * grouping_attributes
	 */
	private Collection<AttributeVariable> grouping_attributes = new ArrayList<AttributeVariable>();
	
	/**
	 * grouping_variables
	 */
	private Collection<GroupingVaribale> grouping_variables;
	
	public GroupByElement(String elementSql, SqlSentence sentence) {
		super(elementSql, sentence);
	}

	/**
	 * @return the grouping_attributes
	 */
	public Collection<AttributeVariable> getGrouping_attributes() {
		return grouping_attributes;
	}

	/**
	 * @param grouping_attributes the grouping_attributes to set
	 */
	public void setGrouping_attributes(
			Collection<AttributeVariable> grouping_attributes) {
		this.grouping_attributes = grouping_attributes;
	}

	/**
	 * @return the grouping_variables
	 */
	public Collection<GroupingVaribale> getGrouping_variables() {
		return grouping_variables;
	}

	/**
	 * @param grouping_variables the grouping_variables to set
	 */
	public void setGrouping_variables(
			Collection<GroupingVaribale> grouping_variables) {
		this.grouping_variables = grouping_variables;
	}

//	public GroupByElement(String elementSql) {
//		super(elementSql);
//	}

}
