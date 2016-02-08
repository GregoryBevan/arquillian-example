/**
 *
 */
package fr.elgregos.arquillian.example.rest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.elgregos.arquillian.example.domain.Sample;

@RunWith(Arquillian.class) // The Arquillian runner
@RunAsClient // Tells Arquillian that all test methods are run as client
public class SampleRestTest {

	private static final String REST_PATH = "";
	private static final String FIRST_SAMPLE = "firstSample";
	private static final String SECOND_SAMPLE = "secondSample";

	private static Long idSample1;
	private static Long idSample2;

	@Deployment // Describes the deployed archive
	public static WebArchive createDeployment() {
		final List<JavaArchive> dependencies = Maven.resolver()
				.loadPomFromFile("pom.xml")
				.importRuntimeDependencies()
				.resolve()
				.withTransitivity()
				.asList(JavaArchive.class);
		return ShrinkWrap.create(WebArchive.class)
				.addPackages(true, "fr.elgregos.arquillian.example")
				.addAsLibraries(dependencies)
				.setWebXML("web.xml");
	}

	@Test
	@InSequence(1) // To order test methods
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public void testCreate(@ArquillianResteasyResource(REST_PATH) final ISampleRest sampleRest) {
		final Response response1 = sampleRest.create(FIRST_SAMPLE);
		assertEquals(Status.ACCEPTED.getStatusCode(), response1.getStatus());
		idSample1 = response1.readEntity(Sample.class).getId(); // Keep it for next test methods
		response1.close();
		final Response response2 = sampleRest.create(SECOND_SAMPLE);
		assertEquals(Status.ACCEPTED.getStatusCode(), response2.getStatus());
		idSample2 = response2.readEntity(Sample.class).getId();
		response2.close();
	}

	@Test
	@InSequence(2)
	@Consumes(MediaType.APPLICATION_JSON)
	public void get_simple_test(@ArquillianResteasyResource(REST_PATH) final ISampleRest sampleRest) {
		final Response response = sampleRest.get(idSample1);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(FIRST_SAMPLE, response.readEntity(Sample.class).getName());
	}

	@Test
	@Consumes(MediaType.APPLICATION_JSON)
	public void get_wrong_id_test(@ArquillianResteasyResource(REST_PATH) final ISampleRest sampleRest) {
		final Response response = sampleRest.get(0L);
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	@InSequence(3)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	public void get_more_flexible_test(@ArquillianResteasyResource(REST_PATH) final ResteasyWebTarget webTarget) {
		final Builder invocationBuilder = webTarget.path(String.valueOf(idSample2)).request();
		invocationBuilder.acceptEncoding("€tUTF-8");
		final Response response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(SECOND_SAMPLE, response.readEntity(Sample.class).getName());
	}

	@Test
	@InSequence(4)
	@Consumes(MediaType.WILDCARD)
	public void delete_simple_test(@ArquillianResteasyResource(REST_PATH) final ISampleRest sampleRest) {
		final Response response1 = sampleRest.delete(idSample1);
		assertEquals(Status.NO_CONTENT.getStatusCode(), response1.getStatus());
		response1.close();
	}

	@Test
	@InSequence(5)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	public void delete_more_flexible_test(@ArquillianResteasyResource(REST_PATH) final ResteasyWebTarget webTarget) {
		final Builder invocationBuilder = webTarget.path(String.valueOf(idSample2)).request();
		final Response response = invocationBuilder.delete();
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

}
