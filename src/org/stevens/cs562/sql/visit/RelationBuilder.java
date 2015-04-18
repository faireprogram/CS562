package org.stevens.cs562.sql.visit;

import java.util.Collection;
import java.util.List;

import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.sqlimpl.AggregateExpression;
import org.stevens.cs562.sql.sqlimpl.GroupByElement;
import org.stevens.cs562.sql.sqlimpl.GroupingVaribale;
import org.stevens.cs562.sql.sqlimpl.IntegerExpression;
import org.stevens.cs562.sql.sqlimpl.SimpleExpression;
import org.stevens.cs562.sql.sqlimpl.SqlSentence;
import org.stevens.cs562.sql.sqlimpl.SuchThatElement;
import org.stevens.cs562.sql.visit.RelationBuilderVisitor.Pair;
import org.stevens.cs562.utils.StringBuilder;
import org.stevens.cs562.utils.graph.AdjacentList;
import org.stevens.cs562.utils.graph.AdjacentNode;
import org.stevens.cs562.utils.graph.AdjacentNodeImpl;
import org.stevens.cs562.utils.graph.TopologicalGraph;

public class RelationBuilder {

	/**
	 * vistor
	 */
	private RelationBuilderVisitor vistor;
	
	/**
	 * expressions
	 */
	private SqlSentence sqlsetence;

	public RelationBuilder( SqlSentence sqlsetence) {
		super();
		this.sqlsetence = sqlsetence;
		this.vistor = new RelationBuilderVisitor();
	}
	
	public RelationBuilder( SqlSentence sqlsetence, RelationBuilderVisitor visitor) {
		super();
		this.sqlsetence = sqlsetence;
		this.vistor = visitor;
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
		
//		System.out.println("Current Layer is " + layers.size());
		return layers;
	}
	
	// At least expression is AggregateExpression
	private void dealWithAggregateExpression(Expression expression1, Expression expression2, AdjacentList<GroupingVaribale, AdjacentNode<GroupingVaribale>> list) {
		// prior_less_exp depends on prior_exp
		if(expression1 instanceof IntegerExpression || expression2 instanceof IntegerExpression) {
			return;
		}
		Expression avg_exp = null;
		Expression common_exp = null;
		if(expression1 instanceof AggregateExpression) {
			avg_exp = expression1;
			common_exp = expression2;
		} else if(expression2 instanceof AggregateExpression) {
			common_exp = expression1;
			avg_exp = expression2;
		}
		
		Variable avg_variable = avg_exp.getVariable().getBelong();
		Variable common_variable = common_exp.getVariable().getBelong();
		
		AdjacentNode<GroupingVaribale> node_avg = list.get((GroupingVaribale)avg_variable);
		AdjacentNode<GroupingVaribale> node_common = list.get((GroupingVaribale)common_variable);
		node_avg.getEdgeVetex().add(node_common);
		
		
	}
	
	/**
	 * Get All pair From the list by the specified Aggregation
	 */
	public Expression getSuchThatBlockExpressionByVariable(GroupingVaribale group_variable) {
		RelationBuilderVisitor innerVisitor = new RelationBuilderVisitor();
		Expression final_resul = null;
		SuchThatElement suchThatElement = sqlsetence.getSuchThatElement();
		for(Expression expresion : suchThatElement.getSuch_that_expressions()) {
			boolean find = false;
			innerVisitor.getPairs().clear();
			expresion.accept(innerVisitor);
			
			/*
			 *  analyze the pair
			 */
			for(Pair pair : innerVisitor.getPairs()) {
				if(pair.getLeft() instanceof  AggregateExpression) { // its dependecy should be it, So group_variable should equal right
					if(StringBuilder.isEqual(pair.getRight().getVariable().getBelong().getName(), group_variable.getName())) {
						find = true;
						break;
					}
				} else if(pair.getRight() instanceof  AggregateExpression) { // its dependecy should be it, So group_variable should equal right
					if(StringBuilder.isEqual(pair.getLeft().getVariable().getBelong().getName(), group_variable.getName())) {
						find = true;
						break;
					}
				} else if(pair.getLeft() instanceof SimpleExpression) {
					if(StringBuilder.isEqual(pair.getLeft().getVariable().getBelong().getName(), group_variable.getName())) {
						find = true;
						break;
					}
				} else if(pair.getRight() instanceof SimpleExpression) {
					if(StringBuilder.isEqual(pair.getRight().getVariable().getBelong().getName(), group_variable.getName())) {
						find = true;
						break;
					}
				}
			}
			
			if(find) {
				final_resul = expresion;
				break;
			}
			
		}
		
		
		return final_resul;
	}
	
	
}
