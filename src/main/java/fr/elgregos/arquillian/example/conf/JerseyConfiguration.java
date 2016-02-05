package fr.elgregos.arquillian.example.conf;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
	register(RequestContextFilter.class);
	register(JacksonFeature.class);
	packages(true, "io.github.tomacla.failsafe.sample.rest");
    }

}