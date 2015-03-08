package org.stevens.cs562.sql;

/**
 * @author faire_000
 *
 */
public enum ComparisonAndComputeOperator {
	GREATER(">"),GREATER_EQUAL(">="),LESS("<"),LESS_EQUAL("<="),EQUAL("="), NOT_EQUAL("<>"),
	ADDITION("+"), MINUS("-"), MULTIPLICATION("*"), DIVID("/"), AND("and"), OR("or");
	
	private String name;
	private String symbol;
	private ComparisonAndComputeOperator(String symbol) {
//		this.name = name;
		this.symbol = symbol;
	}
	public String getName() {
		return name;
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
