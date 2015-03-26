package org.stevens.cs562.sql.sqlimpl;

import java.util.HashMap;
import java.util.Set;

import org.stevens.cs562.sql.Expression;
import org.stevens.cs562.sql.visit.AggregateExpressionVisitorImpl;
import org.stevens.cs562.sql.visit.RelationBuilder;
import org.stevens.cs562.utils.Constants;
import org.stevens.cs562.utils.GeneratorHelper;
import org.stevens.cs562.utils.SQLStringParsers;
import org.stevens.cs562.utils.StringBuilder;

public class SqlSentence {

	/**
	 * selectElement
	 */
	private SelectElement selectElement;

	/**
	 * fromElement
	 */
	private FromElement fromElement;

	/**
	 * whereElement
	 */
	private WhereElement whereElement;

	/**
	 * groupByElement
	 */
	private GroupByElement groupByElement;

	/**
	 * havingElement
	 */
	private HavingElement havingElement;

	/**
	 * suchThatElement
	 */
	private SuchThatElement suchThatElement;

	/**
	 * HashMap
	 */
	private HashMap<String, GroupingVaribale> grouping_variable_dic;
	
	/**
	 * HashMap
	 */
	private HashMap<String, String> attributes_type;

	public SqlSentence(String sql) {
		String[] tmp = SQLStringParsers.parseString(sql);
		/* THIS SHOULD BE FIRST */
		groupByElement = new GroupByElement(tmp[3], this);
		fromElement = new FromElement(tmp[1], this);
		/* THIS SHOULD BE SECOND */
		selectElement = new SelectElement(tmp[0], this);
		/* THIS SHOULD BE LAST */
		whereElement = new WhereElement(tmp[2], this);
		suchThatElement = new SuchThatElement(tmp[4], this);
		havingElement = new HavingElement(tmp[5], this);
	}

	/**
	 * @return the selectElement
	 */
	public SelectElement getSelectElement() {
		return selectElement;
	}

	/**
	 * @return the fromElement
	 */
	public FromElement getFromElement() {
		return fromElement;
	}

	/**
	 * @return the whereElement
	 */
	public WhereElement getWhereElement() {
		return whereElement;
	}

	/**
	 * @return the groupByElement
	 */
	public GroupByElement getGroupByElement() {
		return groupByElement;
	}

	/**
	 * @return the havingElement
	 */
	public HavingElement getHavingElement() {
		return havingElement;
	}

	/**
	 * @return the suchThatElement
	 */
	public SuchThatElement getSuchThatElement() {
		return suchThatElement;
	}

	/**
	 * @return the grouping_variable_dic
	 */
	public HashMap<String, GroupingVaribale> getGrouping_variable_dic() {
		if(grouping_variable_dic == null) {
			grouping_variable_dic = new HashMap<String, GroupingVaribale>();
			GroupingVaribale varible_zero = new GroupingVaribale(Constants.GROUPING_ZERO, String.valueOf(0));
			grouping_variable_dic.put(Constants.GROUPING_ZERO, varible_zero);
		}
		return grouping_variable_dic;
	}
	
	

	/**
	 * @return the attributes_type
	 */
	public HashMap<String, String> getAttributes_type() {
		if(attributes_type == null) {
			attributes_type = new HashMap<String, String>();
		}
		return attributes_type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return selectElement.toString() + fromElement.toString() + whereElement.toString() + groupByElement.toString() + suchThatElement.toString() + havingElement.toString();
	}

	public String getRelationAlgebra() {
		String final_string = "";
		String select_attributes = "SELECT ATTRIBUTES : \n";
		select_attributes += selectElement.toString() + "\n";
		final_string += select_attributes  + "\n";
		
		String grouping_variable = "GROUPING VARIABLE NUM : \n";
		grouping_variable += this.getGrouping_variable_dic().size() + "\n";
		final_string += grouping_variable + "\n";
		
		String grouping_attributes = "GROUPING ATTRIBUTES : \n";
		grouping_attributes += this.getGroupByElement().getGroupingAttributesString() + "\n";
		final_string += grouping_attributes + "\n";
		
		String aggregate_functions = "AGGREGATE FUNCTION LIST : \n";
		aggregate_functions += getAggregateString() + "\n";
		final_string += aggregate_functions;
		
		String predicates = "PREDICATES  LIST : \n";
		predicates += getPredicatesString() + "\n";
		final_string += predicates;
		
		String having = "HAVING   : \n";
		having += this.getHavingElement().toString() + "\n";
		final_string += having;
		return final_string;
	}
	
	private String getAggregateString() {
		String aggregate = "";
		AggregateExpressionVisitorImpl visitor = new AggregateExpressionVisitorImpl();
		GeneratorHelper.getAllAggregateExpression(visitor, this);
		Set<AggregateExpression> expressions = visitor.getAggregate_expression();
		//--------------------find the reasonable aggregates
		for(GroupingVaribale variable : this.getGrouping_variable_dic().values()) {
			String tmp = "{";
			String result = "";
			for(AggregateExpression expression : expressions) {
				if(expression.getVariable().getBelong().equals(variable)) {
					result += expression.getConvertionName()  + ", ";
				}
			}
			if(StringBuilder.isEmpty(result)) {
				result = "NO AGGREGATES";
			} else {
				result = result.replaceAll(",\\s+$", "");
			}
			tmp +=  result + "}=>" + variable.toString() + "\n";
			aggregate += tmp;
		}
		
		return aggregate;
	}
	
	private String getPredicatesString() {
		String predicates = "";
		RelationBuilder relationBuilder = new RelationBuilder(this);
		//--------------------find the reasonable aggregates
		for(GroupingVaribale variable : this.getGrouping_variable_dic().values()) {
			String tmp = "{";
			Expression expression = relationBuilder.getSuchThatBlockExpressionByVariable(variable);
			if(expression != null) {
				tmp += expression.toString();
			} else {
				tmp += "NO PREDICATES";
			}
			
			tmp += "}=>" + variable.toString() + "\n";
			predicates += tmp;
		}
		return predicates;
	}
	
}
