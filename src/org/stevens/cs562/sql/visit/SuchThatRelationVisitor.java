package org.stevens.cs562.sql.visit;

import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.IntegerExpression;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;
import org.stevens.cs562.sql.sqlimpl.StringExpression;

public class SuchThatRelationVisitor extends RelationBuilderVisitor{

	private LeftRightSingleLayerVisitor lrvisitor = new LeftRightSingleLayerVisitor();
	
	
	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression)
	 */
	@Override
	public void visit(ComparisonAndComputeExpression expression) {
		this.lrvisitor.visit(expression);
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
			 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.IntegerExpression)
			 */
			@Override
			public void visit(IntegerExpression expression) {
				initialize_pair(expression);
			}
			
			/* (non-Javadoc)
			 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.StringExpression)
			 */
			@Override
			public void visit(StringExpression expression) {
				initialize_pair(expression);
			}



			/* (non-Javadoc)
			 * @see org.stevens.cs562.sql.visit.AbstractVisitor#visit(org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression)
			 */
			@Override
			public void visit(ComparisonAndComputeExpression expression) {
				if(isAlgorith(expression)) {
					return;
				}
				if(expression.getLeft() instanceof ComparisonAndComputeExpression && !(expression.getRight() instanceof ComparisonAndComputeExpression)) {
					initialize_pair(expression.getRight(), (ComparisonAndComputeExpression)expression.getLeft());
					return;
				}
				if(expression.getRight() instanceof ComparisonAndComputeExpression && !(expression.getLeft() instanceof ComparisonAndComputeExpression)) {
					initialize_pair(expression.getLeft(), (ComparisonAndComputeExpression)expression.getRight());
					return;
				}
				
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
			
			private void initialize_pair(Expression expression1, ComparisonAndComputeExpression expression2) {
				AggregateExpressionVisitorImpl aggregation_visitor = new AggregateExpressionVisitorImpl();
				aggregation_visitor.visit(expression2.getLeft());
				aggregation_visitor.visit(expression2.getRight());
				for(AggregateExpression agg_exp : aggregation_visitor.getAggregate_expression()) {
					Pair pair = new Pair();
					pair.setLeft(expression1);
					pair.setRight(agg_exp);
					pairs.add(pair);
				}
			}
	}
			
}
