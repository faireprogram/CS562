package org.stevens.cs562.sql.exception;

/**
 * @author faire_000
 *
 */
public class SqlSyntaxException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8460295319292714685L;

	private  String error_message;
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return super.getMessage();
	}



	public SqlSyntaxException(String error_message) {
		super();
		this.error_message = error_message;
	}
	
	

	
}
