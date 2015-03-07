package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.AbstractVariable;

public class ConstantVariable extends AbstractVariable{

	/**
	 * Integer
	 */
	private Integer value;
	
	

	/**
	 * @param value
	 */
	public ConstantVariable(Integer value) {
		super();
		this.value = value;
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
	
	
}
