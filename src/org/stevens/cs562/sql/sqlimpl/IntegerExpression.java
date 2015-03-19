package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.AbstractExpression;
import org.stevens.cs562.sql.ComparisonAndComputeOperator;

/**
 * @author faire_000
 *
 */
public class IntegerExpression extends AbstractExpression{

	private Integer value;
	
	public IntegerExpression(Integer value) {
		this.value = value;
	}
	
	public void compute(IntegerExpression other, ComparisonAndComputeOperator operator) {
		if(operator.equals(ComparisonAndComputeOperator.ADDITION)) {
			value = value + other.getValue();
		}
		if(operator.equals(ComparisonAndComputeOperator.MINUS)) {
			value = value - other.getValue();
		}
		if(operator.equals(ComparisonAndComputeOperator.MULTIPLICATION)) {
			value = value * other.getValue();
		}
		if(operator.equals(ComparisonAndComputeOperator.DIVID)) {
			value = value / other.getValue();
		}
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(value);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getValue().hashCode() * 5;
	}
	
}
