package org.stevens.cs562.sql.visit;

import java.util.ArrayList;
import java.util.List;

import org.stevens.cs562.sql.ComparisonAndComputeOperator;
import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.IntegerExpression;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;
import org.stevens.cs562.sql.sqlimpl.StringExpression;

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
		 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression)
		 */
		@Override
		public void visit(ComparisonAndComputeExpression expression) {
			
			filterExpression(expression);
			
		}
		
		private boolean isAndOrOr(ComparisonAndComputeExpression expression) {
			return expression.getOperator().equals(ComparisonAndComputeOperator.AND) || expression.getOperator().equals(ComparisonAndComputeOperator.OR);
		}
		
		private boolean isAlgorith(ComparisonAndComputeExpression expression) {
			return expression.getOperator().equals(ComparisonAndComputeOperator.ADDITION) || expression.getOperator().equals(ComparisonAndComputeOperator.MULTIPLICATION) 
					|| expression.getOperator().equals(ComparisonAndComputeOperator.MINUS) || expression.getOperator().equals(ComparisonAndComputeOperator.DIVID) ;
		}
		
		private boolean isCompare(ComparisonAndComputeExpression expression) {
			return !isAndOrOr(expression) && !isAlgorith(expression);
		}
		
		private void filterExpression(Expression expression) {
			
			if(expression instanceof ComparisonAndComputeExpression) {
				List<Expression> left_expressions = new ArrayList<Expression>();
				List<Expression> right_expressions = new ArrayList<Expression>();
				ComparisonAndComputeExpression tmp = (ComparisonAndComputeExpression)expression;
				if(isCompare(tmp)) {// 1.quant >= (quant  + z.quant)
					AggregateExpressionVisitorImpl agg_visit1 = new AggregateExpressionVisitorImpl();
					SimpleExpressionVisitorImpl simple_visit1 = new SimpleExpressionVisitorImpl();
					agg_visit1.visit(tmp.getLeft());
					simple_visit1.visit(tmp.getLeft());
					left_expressions.addAll(agg_visit1.getAggregate_expression());
					left_expressions.addAll(simple_visit1.getSimples_expression());
					
					AggregateExpressionVisitorImpl agg_visit2 = new AggregateExpressionVisitorImpl();
					SimpleExpressionVisitorImpl simple_visit2 = new SimpleExpressionVisitorImpl();
					agg_visit2.visit(tmp.getRight());
					simple_visit2.visit(tmp.getRight());
					right_expressions.addAll(agg_visit2.getAggregate_expression());
					right_expressions.addAll(simple_visit2.getSimples_expression());
					
					initialize_pair(left_expressions, right_expressions);
				}
				if(isAndOrOr(tmp)) {// 1.quant >= (quant  + z.quant) and 1.month = 1 and 1.prod = prod
					filterExpression(((ComparisonAndComputeExpression) expression).getLeft());
					filterExpression(((ComparisonAndComputeExpression) expression).getRight());
				}
			} 
			
		}

		
		private void initialize_pair(List<Expression> left_expressions, List<Expression> right_expression) {
			for(Expression left : left_expressions)
				for(Expression right : right_expression) {
					Pair pair = new Pair();
					pair.setLeft(left);
					pair.setRight(right);
					pairs.add(pair);
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
