package exceptions;

public class MakeErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6951671567433876324L;

	public MakeErrorException() {
		super();
	}

	public MakeErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MakeErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public MakeErrorException(String message) {
		super(message);
	}

	public MakeErrorException(Throwable cause) {
		super(cause);
	}

}
