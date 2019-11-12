package com.sandesh.jersey.exceptions;

// Similar to these exceptions we have WebApplicationException which was already implemented by spring
// This application class also has other subclass exception which can be readily used as the mapper has been already given by the library itself.
// Check for the documentation for the further clarification
public class DataNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public DataNotFoundException(String message) {
		super(message);
	}
}
