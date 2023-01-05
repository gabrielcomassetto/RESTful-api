package webservices.model.exceptions;

public class DAOException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6967579856757530371L;
	
	private int code;
	
	public DAOException(String message, int code) {
		
		super(message);
		this.code = code;
	}
	
	public int getCode() {
		
		return code;
	}
}
