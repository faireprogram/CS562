package org.stevens.cs562.sql.sqlimpl;

import org.stevens.cs562.sql.AbstractVariable;

//Generic T belongs to prototype of the JAVA
//String, Integer, Number
public class ConstantVariable<T> extends AbstractVariable{

	/**
	 * Integer
	 */
	private T value;
	
	

	/**
	 * @param value
	 */
	public ConstantVariable(T value) {
		super();
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}
	
	
}
