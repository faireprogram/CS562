package org.stevens.cs562.sql;

/**
 * @author faire_000
 *
 */
public enum ComparisonAndComputeOperator {
	GREATER(">",">"),GREATER_EQUAL(">=",">="),LESS("<","<"),LESS_EQUAL("<=","<="),EQUAL("=","=="), NOT_EQUAL("<>","!="),
	ADDITION("+", "+"), MINUS("-", "-"), MULTIPLICATION("*", "*"), DIVID("/", "/"), AND("and", "&&"), OR("or", "||");
	
	private String java_name;
	private String symbol;
	private ComparisonAndComputeOperator(String symbol, String java_name) {
		this.java_name = java_name;
		this.symbol = symbol;
	}
	
	/**
	 * @return the java_name
	 */
	public String getJava_name() {
		return java_name;
	}


	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.symbol;
	}
	
	
	
}
