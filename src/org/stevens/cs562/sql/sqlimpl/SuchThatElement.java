package org.stevens.cs562.sql.sqlimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.stevens.cs562.sql.AbstractSqlElement;
import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.utils.SQLStringParsers;


/**
 * 
 * @author faire_000
 *
 */
public class SuchThatElement extends AbstractSqlElement{

	/**
	 * groupping_variable
	 */
	private List<Expression> such_that_expressions;
	
//	private List<>
	
//	private Collection
	public SuchThatElement(String elementSql, SqlSentence sentence) {
		super(elementSql, sentence);
	}

	/* (non-Javadoc)
	 * GET A BUNCH OF Expression describe the relationship between them
	 */
	@Override
	protected void convert(String elementSql) {

		SQLStringParsers.parseStringToSuchThatElement(elementSql, this);
		
	}

	/**
	 * @return the such_that_expressions
	 */
	public List<Expression> getSuch_that_expressions() {
		if(such_that_expressions == null) {
			such_that_expressions = new ArrayList<Expression>();
		} 
		return such_that_expressions;
	}

	/**
	 * @param such_that_expressions the such_that_expressions to set
	 */
	public void setSuch_that_expressions(List<Expression> such_that_expressions) {
		this.such_that_expressions = such_that_expressions;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Iterator<Expression> iterator = this.getSuch_that_expressions().iterator();
		String final_to_string = null;
		while(iterator.hasNext()) {
			final_to_string += iterator.next().toString();
		}
		return final_to_string;
	}
	
	

}
