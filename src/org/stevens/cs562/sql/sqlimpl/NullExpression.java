package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.visit.Visitor;

public class NullExpression implements Expression{

	public void accept(Visitor visitor) {
		
	}

	public void caculate() {
		
	}

	public Expression getExpression() {
		return new NullExpression();
	}

	public Variable getVariable() {
		return new NullVariable();
	}

}
