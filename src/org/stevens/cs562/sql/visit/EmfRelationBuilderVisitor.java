package org.stevens.cs562.sql.visit;

import java.util.ArrayList;
import java.util.List;

import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression;
import org.stevens.cs562.sql.sqlimpl.GroupingVaribale;

public class EmfRelationBuilderVisitor extends RelationBuilderVisitor{

	GroupingVaribale zero;
	
	public EmfRelationBuilderVisitor(GroupingVaribale zero) {
		this.zero = zero;
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.visit.RelationBuilderVisitor#visit(org.stevens.cs562.sql.sqlimpl.ComparisonAndComputeExpression)
	 */
	@Override
	public void visit(ComparisonAndComputeExpression expression) {
		super.visit(expression);
		
		List<Pair> tmpPairs = new ArrayList<Pair>();
		
		//remove all grouping zero
		for(Pair pair : this.getPairs()) {
			Expression left = pair.getLeft();
			Expression right = pair.getRight();
			
			if(zero.equals(left.getVariable().getBelong()) || zero.equals(right.getVariable().getBelong())) {
				tmpPairs.add(pair);
			}
		}
		
		this.getPairs().removeAll(tmpPairs);
	}
	
	

}
