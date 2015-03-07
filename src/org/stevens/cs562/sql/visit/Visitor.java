package org.stevens.cs562.sql.visit;

import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;

public interface Visitor {
	
	public void visit(Variable variable);
	
	public void visit(Expression expression);
	
}
