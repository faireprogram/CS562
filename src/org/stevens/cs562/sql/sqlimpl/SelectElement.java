package org.stevens.cs562.sql.sqlimpl;

import java.util.ArrayList;
import java.util.List;

import org.stevens.cs562.sql.AbstractSqlElement;
import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.utils.SQLStringParsers;

public class SelectElement extends AbstractSqlElement {
	
	private List<Expression> projectItems;

	public SelectElement(String elementSql, SqlSentence sentence) {
		super(elementSql, sentence);
		
		
	}

	/**
	 * @return the projectItems
	 */
	public List<Expression> getProjectItems() {
		if(projectItems == null) {
			projectItems = new ArrayList<Expression>();
		}
		return projectItems;
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.AbstractSqlElement#convert(java.lang.String)
	 */
	@Override
	protected void convert(String elementSql) {
		SQLStringParsers.parseStringToSelectElement(elementSql, this);
	}

	
	
}
