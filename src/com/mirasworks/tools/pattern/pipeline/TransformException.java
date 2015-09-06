package com.mirasworks.tools.pattern.pipeline;

/**
 * 
 * @author Koda
 * Used when a type cannot be transform in another type
 *
 */
public class TransformException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6021381744413397554L;

	public TransformException() {
		super();
	}

	public TransformException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TransformException(String message, Throwable cause) {
		super(message, cause);
	}

	public TransformException(String message) {
		super(message);
	}

	public TransformException(Throwable cause) {
		super(cause);
	}

}
