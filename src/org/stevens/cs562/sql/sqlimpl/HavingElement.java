package org.stevens.cs562.sql.sqlimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.stevens.cs562.sql.AbstractSqlElement;
import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.utils.SQLStringParsers;

public class HavingElement extends AbstractSqlElement {

	private List<Expression> having_expressions;
	
	public HavingElement(String elementSql, SqlSentence sentence) {
		super(elementSql,sentence);
	}
	
	

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.AbstractSqlElement#convert(java.lang.String)
	 */
	@Override
	protected void convert(String elementSql) {
		SQLStringParsers.parseStringToHavingElement(elementSql, this);
	}



	/**
	 * @return the having_expressions
	 */
	public List<Expression> getHaving_expressions() {
		if(having_expressions == null) {
			having_expressions = new ArrayList<Expression>();
		}
		return having_expressions;
	}

	/**
	 * @param having_expressions the having_expressions to set
	 */
	public void setHaving_expressions(List<Expression> having_expressions) {
		this.having_expressions = having_expressions;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Iterator<Expression> iterator = this.getHaving_expressions().iterator();
		String final_to_string = null;
		while(iterator.hasNext()) {
			final_to_string += iterator.next().toString();
		}
		return final_to_string;
	}
	
	
	
}
