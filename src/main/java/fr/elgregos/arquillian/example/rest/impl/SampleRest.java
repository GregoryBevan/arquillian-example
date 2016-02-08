package fr.elgregos.arquillian.example.rest.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.elgregos.arquillian.example.domain.Sample;
import fr.elgregos.arquillian.example.rest.ISampleRest;
import fr.elgregos.arquillian.example.service.SampleService;

@Service
public class SampleRest implements ISampleRest {

	@Autowired
	private SampleService sampleService;

	@Override
	public Response create(final String name) {
		final Sample entity = this.sampleService.create(name);
		return Response.accepted(entity).build();
	}

	@Override
	public Response delete(final Long id) {
		this.sampleService.delete(id);
		return Response.noContent().build();
	}

	@Override
	public Response get(final Long id) {
		final Sample entity = this.sampleService.get(id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

}