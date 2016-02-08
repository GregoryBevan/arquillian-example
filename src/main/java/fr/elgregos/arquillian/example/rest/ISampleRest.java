package fr.elgregos.arquillian.example.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public interface ISampleRest {

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	Response create(String name);

	@DELETE
	@Path("{id}")
	Response delete(@PathParam("id") Long id);

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	Response get(@PathParam("id") Long id);

}