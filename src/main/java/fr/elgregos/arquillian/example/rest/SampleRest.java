package fr.elgregos.arquillian.example.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.http.MediaType;

import fr.elgregos.arquillian.example.domain.Sample;
import fr.elgregos.arquillian.example.service.SampleService;

@Path("/")
public class SampleRest {

    private final SampleService sampleService;

    @Inject
    public SampleRest(SampleService sampleService) {
	this.sampleService = sampleService;
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response create(String name) {
	Sample entity = sampleService.create(name);
	return Response.accepted(entity).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
	sampleService.delete(id);
	return Response.accepted().build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response get(@PathParam("id") Long id) {
	Sample entity = sampleService.get(id);
	if (entity == null) {
	    return Response.status(Status.NOT_FOUND).build();
	}
	return Response.ok(entity).build();
    }

}