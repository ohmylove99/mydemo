package org.octopus.api.exception;

public class BookNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3961367372795556895L;

	public BookNotFoundException(Long id) {
        super("Book id not found : " + id);
    }

}