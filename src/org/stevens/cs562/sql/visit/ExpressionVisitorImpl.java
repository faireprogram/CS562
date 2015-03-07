package org.stevens.cs562.sql.visit;

import java.util.ArrayList;
import java.util.List;

import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;

public class ExpressionVisitorImpl extends AbstractVisitor{
	
	private Visitor leftVisitor =  new LeftComparisonVisitor();
	private Visitor rightVisitor =  new RightComparisonVisitor();
	private List<Variable> leftVariables = new ArrayList<Variable>();
	private List<Variable> rightVariables = new ArrayList<Variable>();

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression)
	 */
	@Override
	public void visit(ComparisonAndComputeExpression expression) {
		expression.accept(leftVisitor);
		expression.accept(rightVisitor);
	}
	
	public void test() {
		System.out.println(leftVariables.get(0).toString());
	}
	
	/**
	 * @author faire_000
	 *
	 */
	private class LeftComparisonVisitor extends AbstractVisitor {

		/* (non-Javadoc)
		 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.SimpleExpression)
		 */
		@Override
		public void visit(SimpleExpression expression) {
			leftVariables.add(expression.getAttribute());
		}

		/* (non-Javadoc)
		 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression)
		 */
		@Override
		public void visit(ComparisonAndComputeExpression expression) {
			expression.getLeft().accept(this);
		}

		/* (non-Javadoc)
		 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.AggregateExpression)
		 */
		@Override
		public void visit(AggregateExpression expression) {
			leftVariables.add(expression.getAttributes());
		}
		
	}
	
	/**
	 * @author faire_000
	 *
	 */
	private class RightComparisonVisitor extends AbstractVisitor {

		/* (non-Javadoc)
		 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.SimpleExpression)
		 */
		@Override
		public void visit(SimpleExpression expression) {
			rightVariables.add(expression.getAttribute());
		}

		/* (non-Javadoc)
		 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression)
		 */
		@Override
		public void visit(ComparisonAndComputeExpression expression) {
			expression.getRight().accept(this);
		}

		/* (non-Javadoc)
		 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.AggregateExpression)
		 */
		@Override
		public void visit(AggregateExpression expression) {
			rightVariables.add(expression.getAttributes());
		}
		
	}
	
}
