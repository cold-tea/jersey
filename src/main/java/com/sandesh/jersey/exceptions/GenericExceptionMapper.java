package com.sandesh.jersey.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.sandesh.jersey.models.ErrorMessage;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception>{

	@Override
	public Response toResponse(Exception exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 500, "http://localhost:8080/jersey/webapi/messages/1");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
	}

}
