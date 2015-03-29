package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.AbstractExpression;
import org.stevens.cs562.sql.AggregateOperator;
import org.stevens.cs562.sql.Variable;
import org.stevens.cs562.sql.visit.Visitor;
import org.stevens.cs562.utils.Constants;

/**
 * @author faire_000
 *
 */
public class AggregateExpression extends AbstractExpression{

	/**
	 * AggregateOperator
	 */
	private AggregateOperator operator;
	
	/**
	 * attributes
	 */
	private Variable attributes;

	public AggregateExpression(AggregateOperator operator, Variable attributes) {
		super();
		this.operator = operator;
		this.attributes = attributes;
	}

	/**
	 * @return the operator
	 */
	public AggregateOperator getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(AggregateOperator operator) {
		this.operator = operator;
	}

	/**
	 * @return the attributes
	 */
	public Variable getVariable() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setVariable(Variable attributes) {
		this.attributes = attributes;
	}

	public void visit() {
		
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(!this.attributes.getBelong().getName().equals(Constants.GROUPING_ZERO)) {
			return this.operator.getName() + "(" + this.attributes.getBelong().getName() + "." + this.attributes.getName() + ")";
		}
		return this.operator.getName() + "(" + this.attributes.getName() + ")";
	}

	/* (non-Javadoc)
	 * @see org.stevens.cs562.sql.AbstractExpression#getConvertionName()
	 */
	@Override
	public String getConvertionName() {
//		if(this.getVariable().getBelong() != null && !this.getVariable().getBelong().equals(Constants.GROUPING_ZERO)) {
//			return ( "_" + this.getVariable().getBelong().getAlias() + "_" + getVariable().getName() + "_" + getOperator()).toLowerCase();
//		}
//		return (this.getVariable().getName() + "_" +getOperator()).toLowerCase();
		
		return ( "_" + this.getVariable().getBelong().getAlias() + "_" + getVariable().getName() + "_" + getOperator()).toLowerCase();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AggregateExpression) {
			AggregateExpression exp = (AggregateExpression)obj;
			if(exp.getOperator().equals(this.getOperator()) && exp.getVariable().equals(this.getVariable())) {
				return true;
			}
		}
		return false;
	}
	
	
	public String getSumCountName() {
		String str =  "_" + this.getVariable().getBelong().getAlias() + "_" + getVariable().getName() + "_" + "sum,";
		   str +=  "_" + this.getVariable().getBelong().getAlias() + "_" + getVariable().getName() + "_" + "count";
		   return str;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getVariable().hashCode() *5 + this.getOperator().hashCode() * 31;
	}
	
	
	
	
	
	
	
	
}
