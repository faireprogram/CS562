package org.stevens.cs562.sql.visit;

import java.util.Collection;
import java.util.List;

import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.GroupByElement;
import org.stevens.cs562.sql.sqlimpl.GroupingVaribale;
import org.stevens.cs562.sql.sqlimpl.SqlSentence;
import org.stevens.cs562.sql.sqlimpl.SuchThatElement;
import org.stevens.cs562.sql.visit.RelationBuilderVisitor.Pair;
import org.stevens.cs562.utils.graph.AdjacentList;
import org.stevens.cs562.utils.graph.AdjacentNode;
import org.stevens.cs562.utils.graph.AdjacentNodeImpl;
import org.stevens.cs562.utils.graph.TopologicalGraph;

public class RelationBuilder {

	/**
	 * vistor
	 */
	private RelationBuilderVisitor vistor = new RelationBuilderVisitor();
	
	/**
	 * expressions
	 */
	private SqlSentence sqlsetence;

	public RelationBuilder( SqlSentence sqlsetence) {
		super();
		this.sqlsetence = sqlsetence;
	}
	
	public List<Collection<AdjacentNode<GroupingVaribale>>> build() {
		GroupByElement gbElement = sqlsetence.getGroupByElement();
		Collection<GroupingVaribale> variables = gbElement.getGrouping_variables();
		AdjacentList<GroupingVaribale, AdjacentNode<GroupingVaribale>> list = new AdjacentList<GroupingVaribale, AdjacentNode<GroupingVaribale>>();
		// add a indegree variable list
		for(GroupingVaribale v : variables) {
			AdjacentNode<GroupingVaribale> t = new AdjacentNodeImpl<GroupingVaribale>(null,v);
			list.add(t);
		}
		
		SuchThatElement suchThatElement = sqlsetence.getSuchThatElement();
		for(Expression expresion : suchThatElement.getSuch_that_expressions()) {
			expresion.accept(vistor);
		}
		
		for(Pair pair : vistor.getPairs()) {
			Variable v_left = pair.getLeft().getVariable();
			Variable v_right = pair.getRight().getVariable();
			//doesn't in the same group variable
			if(!v_left.getBelong().equals(v_right.getBelong())) {
				// MF Query with Aggregate
				if(pair.getLeft() instanceof AggregateExpression || pair.getRight() instanceof AggregateExpression ) {
					dealWithAggregateExpression(pair.getLeft(), pair.getRight(), list);
				}
				
				// EMF Query to do
			}
		}
		
		TopologicalGraph<GroupingVaribale> grouping_variable_graph = new TopologicalGraph<GroupingVaribale>(list);
		List<Collection<AdjacentNode<GroupingVaribale>>> layers = grouping_variable_graph.sort();
		
		System.out.println("Current Layer is " + layers.size());
		return layers;
	}
	
	// At least expression is AggregateExpression
	private void dealWithAggregateExpression(Expression expression1, Expression expression2, AdjacentList<GroupingVaribale, AdjacentNode<GroupingVaribale>> list) {
		// prior_less_exp depends on prior_exp
		Expression prior_exp = null;
		Expression prior_less_exp = null;
		if(expression1 instanceof AggregateExpression) {
			prior_exp = expression1;
			prior_less_exp = expression2;
		}
		if(expression2 instanceof AggregateExpression) {
			prior_less_exp = expression1;
			prior_exp = expression2;
		}
		
		Variable prior_variable = prior_exp.getVariable().getBelong();
		Variable prior_less_variable = prior_less_exp.getVariable().getBelong();
		
		AdjacentNode<GroupingVaribale> node_prior = list.get((GroupingVaribale)prior_variable);
		AdjacentNode<GroupingVaribale> node_less_prior = list.get((GroupingVaribale)prior_less_variable);
		node_less_prior.getEdgeVetex().add(node_prior);
		
	}
	
}
