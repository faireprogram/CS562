package org.stevens.cs562.sql.sqlimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.stevens.cs562.sql.AbstractSqlElement;
import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.utils.SQLStringParsers;

public class WhereElement extends AbstractSqlElement{

	private List<Expression> where_expressions;
	
	public WhereElement(String elementSql, SqlSentence sentence) {
		super(elementSql, sentence);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.AbstractSqlElement#convert(java.lang.String)
	 */
	@Override
	protected void convert(String elementSql) {
		SQLStringParsers.parseStringToWhereElement(elementSql, this);
	}




	/**
	 * @return the where_expressions
	 */
	public List<Expression> getWhere_expressions() {
		if(where_expressions == null) {
			where_expressions = new ArrayList<Expression>();
		}
		return where_expressions;
	}

	/**
	 * @param where_expressions the where_expressions to set
	 */
	public void setWhere_expressions(List<Expression> where_expressions) {
		this.where_expressions = where_expressions;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Iterator<Expression> iterator = this.getWhere_expressions().iterator();
		String final_to_string = null;
		while(iterator.hasNext()) {
			final_to_string += iterator.next().toString();
		}
		return final_to_string;
	}
	
}
