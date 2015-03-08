package org.stevens.cs562.sql;

import org.stevens.cs562.sql.sqlimpl.NullVariable;
import org.stevens.cs562.sql.visit.Visitor;

/**
 * @author faire_000
 *
 */
public abstract class AbstractExpression  implements Expression{

	
	
	/**
	 * need_excute_first
	 */
	Expression need_excute_first;

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.Expression#caculate()
	 */
	public void caculate() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.visit.Visit#accept(org.stevens.cs562.sql.visit.Visitor)
	 */
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.Expression#getVariable()
	 */
	public Variable getVariable() {
		return new NullVariable();
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.Expression#getConvertionName()
	 */
	public String getConvertionName() {
		return null;
	}
	
}
