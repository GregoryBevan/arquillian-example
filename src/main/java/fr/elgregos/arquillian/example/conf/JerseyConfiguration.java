package fr.elgregos.arquillian.example.conf;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import fr.elgregos.arquillian.example.rest.ISampleRest;
import fr.elgregos.arquillian.example.rest.impl.SampleRest;

public class JerseyConfiguration extends ResourceConfig {

	public JerseyConfiguration() {
		this.register(RequestContextFilter.class);
		this.register(JacksonFeature.class);
		this.packages(true, "fr.elgregos.arquillian.example.rest");
		this.register(new AbstractBinder() {
			@Override
			protected void configure() {
				this.bind(SampleRest.class).to(ISampleRest.class);
			}
		});
	}

}