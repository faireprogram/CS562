package org.stevens.cs562.sql.visit;

import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.Variable;

public interface Visitor {
	
	public void visit(Variable variable);
	
	public void visit(Expression expression);
	
}
