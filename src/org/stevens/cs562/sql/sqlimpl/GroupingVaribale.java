package org.stevens.cs562.sql.sqlimpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.stevens.cs562.sql.AbstractVariable;
import org.stevens.cs562.sql.Variable;

/**
 * @author faire_000
 *
 */
public class GroupingVaribale extends AbstractVariable{

	/**
	 * attributes
	 */
	private Collection<? extends Variable> attributes;
	
	/**
	 * current_predicates
	 */
	private Set<AggregateExpression> all_aggregates;

	public GroupingVaribale(String variable_name) {
		super();
		this.variable_name = variable_name;
	}
	
	public GroupingVaribale(String variable_name, String alias_name) {
		super();
		this.variable_alias = alias_name;
		this.variable_name = variable_name;
	}

	public Variable getBelong() {
		return null;
	}

	public Collection<? extends Variable> getContain() {
		return attributes;
	}

	/**
	 * @return the all_aggregates
	 */
	public Set<AggregateExpression> getAll_aggregates() {
		if(all_aggregates == null) {
			all_aggregates = new HashSet<AggregateExpression>();
		}
		return all_aggregates;
	}


	
}
