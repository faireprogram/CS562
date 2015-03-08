package org.stevens.cs562.sql.sqlimpl;

import java.util.ArrayList;
import java.util.List;

import org.stevens.cs562.sql.AbstractSqlElement;
import org.stevens.cs562.sql.Expression;

public class SelectElement extends AbstractSqlElement {
	
	private List<Expression> projectItems = new ArrayList<Expression>();

	public SelectElement(String elementSql, SqlSentence sentence) {
		super(elementSql, sentence);
		
		
	}

	/**
	 * @return the projectItems
	 */
	public List<Expression> getProjectItems() {
		return projectItems;
	}

	
}
