/**
 * 
 */
package com.obiectumclaro.templates.exception;

/**
 * @author fausto
 *
 */
public class TemplateGenerationException extends Exception {

	private static final long serialVersionUID = 1L;

	public TemplateGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateGenerationException(String message) {
		super(message);
	}

	public TemplateGenerationException(Throwable cause) {
		super(cause);
	}

	
}
