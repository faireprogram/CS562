package org.stevens.cs562.sql.visit;

import java.util.ArrayList;
import java.util.List;

import org.stevens.cs562.sql.AggregateOperator;
import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;
import org.stevens.cs562.utils.graph.AdjacentList;
import org.stevens.cs562.utils.graph.AdjacentNode;

/**
 * @author faire_000
 *
 *
 */
public class RelationBuilderVisitor extends AbstractVisitor{
 
	List<Pair> pairs = new ArrayList<Pair>();
	LeftRightSingleLayerVisitor lrvisitor = new LeftRightSingleLayerVisitor();
	
	boolean isLeftVisit = true;
	
	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression)
	 */
	@Override
	public void visit(ComparisonAndComputeExpression expression) {
		lrvisitor.visit(expression);
	}

	private class LeftRightSingleLayerVisitor extends AbstractVisitor {

		/* (non-Javadoc)
		 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.SimpleExpression)
		 */
		@Override
		public void visit(SimpleExpression expression) {
			initialize_pair(expression);
		}

		/* (non-Javadoc)
		 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression)
		 */
		@Override
		public void visit(ComparisonAndComputeExpression expression) {
			// left visit
			isLeftVisit = true;
			expression.getLeft().accept(this);
			// right visit
			isLeftVisit = false;
			expression.getRight().accept(this);
		}

		/* (non-Javadoc)
		 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.AggregateExpression)
		 */
		@Override
		public void visit(AggregateExpression expression) {
			initialize_pair(expression);
		}
		
		private void initialize_pair(Expression expression) {
			if(isLeftVisit) {
				Pair pair = new Pair();
				pair.setLeft(expression);
				pairs.add(pair);
			} else {
				pairs.get(pairs.size()-1).setRight(expression);
			}
		}
		
	}
	
	public class Pair {
		private Expression left;
		private Expression right;
		/**
		 * @return the left
		 */
		public Expression getLeft() {
			return left;
		}
		/**
		 * @param left the left to set
		 */
		public void setLeft(Expression left) {
			this.left = left;
		}
		/**
		 * @return the right
		 */
		public Expression getRight() {
			return right;
		}
		/**
		 * @param right the right to set
		 */
		public void setRight(Expression right) {
			this.right = right;
		}
		public Pair() {
			super();
		}
	}

	/**
	 * @return the pairs
	 */
	public List<Pair> getPairs() {
		return pairs;
	}
	
	
}
