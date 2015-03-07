package org.stevens.cs562.sql;

/**
 * @author faire_000
 *
 */
public enum AggregateOperator {
	MAX("MAX"),MIN("MIN"),COUNT("COUNT"),AVERAGE("AVERAGE"),SUM("SUM");
	
	private String name;
	private AggregateOperator(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	
}
