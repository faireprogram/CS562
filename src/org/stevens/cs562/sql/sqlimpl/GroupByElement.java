package org.stevens.cs562.sql.sqlimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.stevens.cs562.sql.AbstractSqlElement;
import org.stevens.cs562.utils.SQLStringParsers;

public class GroupByElement extends AbstractSqlElement {

	/**
	 * grouping_attributes
	 */
	private Collection<AttributeVariable> grouping_attributes;
	
	/**
	 * grouping_variables
	 */
	public GroupByElement(String elementSql, SqlSentence sentence) {
		super(elementSql, sentence);
	}

	/**
	 * @return the grouping_attributes
	 */
	public Collection<AttributeVariable> getGrouping_attributes() {
		if(grouping_attributes == null) {
			grouping_attributes = new ArrayList<AttributeVariable>();
		}
		return grouping_attributes;
	}

	/**
	 * @param grouping_attributes the grouping_attributes to set
	 */

	/**
	 * @return the grouping_variables
	 */
	public Collection<GroupingVaribale> getGrouping_variables() {
		return getSelfSentce().getGrouping_variable_dic().values();
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.AbstractSqlElement#convert(java.lang.String)
	 */
	@Override
	protected void convert(String elementSql) {
		SQLStringParsers.parseStringToGroupByElement(elementSql, this);
	}

	/**
	 * @param grouping_attributes the grouping_attributes to set
	 */
	public void setGrouping_attributes(
			Collection<AttributeVariable> grouping_attributes) {
		this.grouping_attributes = grouping_attributes;
	}
	
	

//	public GroupByElement(String elementSql) {
//		super(elementSql);
//	}
	public String toString() {
		String final_to_string = null;
		Iterator<AttributeVariable> iterator_attribute = this.getGrouping_attributes().iterator();
		while(iterator_attribute.hasNext()) {
			final_to_string += iterator_attribute.next().toString() + ", ";
		}
		final_to_string += ":";
		Iterator<GroupingVaribale> iterator = this.getGrouping_variables().iterator();
		
		while(iterator.hasNext()) {
			final_to_string += iterator.next().toString() + ", ";
		}
		return final_to_string;
	}
	

}
