package org.stevens.cs562.sql.sqlimpl;

import java.util.ArrayList;
import java.util.Collection;

import org.stevens.cs562.sql.AbstractSqlElement;
import org.stevens.cs562.sql.Variable;


/**
 * 
 * @author faire_000
 *
 */
public class SuchThatElement extends AbstractSqlElement{

	/**
	 * groupping_variable
	 */
	private Collection<? extends Variable> groupping_variable = new ArrayList<GroupingVaribale>();
	
//	private Collection
	public SuchThatElement(String elementSql) {
		super(elementSql);
	}
	
	

}
