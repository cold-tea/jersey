package com.sandesh.jersey.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sandesh.jersey.models.Message;
import com.sandesh.jersey.resources.beans.MessageFilterBean;
import com.sandesh.jersey.services.MessageService;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
		if (filterBean.getYear() == 0) return messageService.getAllMessages();
		else if (filterBean.getYear() < 0) return null;
		else if (filterBean.getStart() >= 0 && filterBean.getSize() >= 0) return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		else return messageService.getAllMessagesForYear(filterBean.getYear());
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@Context UriInfo uriInfo, @PathParam("messageId") long id) {
		Message message = messageService.getMessage(id);
//		message.addLink(uriInfo.getAbsolutePath().toString(), "self");
		// Creating uri for self
		String uri = uriInfo.getBaseUriBuilder().path(MessageResource.class).
				path(String.valueOf(message.getId())).build().toString();
		message.addLink(uri, "self");
		
		// Creating uri for profiles
		String profileUri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).
				path(message.getAuthor()).build().toString();
		message.addLink(profileUri, "profile");
		
		// Creating uri for comments
		String commentUri = uriInfo.getBaseUriBuilder().path(MessageResource.class)
				.path(MessageResource.class, "getAllComments")
				.path(CommentResource.class).resolveTemplate("messageId", message.getId())
				.build().toString();
		message.addLink(commentUri, "comments");
		return message;
	}
	
	@POST
	public Response postMessage(@Context UriInfo uriInfo, Message message) throws URISyntaxException {
//		return Response.status(Status.CREATED).entity(messageService.addMessage(message)).build();
		Message messageWithId = messageService.addMessage(message);
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(messageWithId.getId())).build();
		return Response.created(uri).entity(messageWithId).build();
	}
	
	@PUT
	public Message putMessage(Message message) {
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public Message deleteMessage(@PathParam("messageId") long id) {	
		return messageService.removeMessage(id);
		
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getAllComments() {
		return new CommentResource();
	}
}
