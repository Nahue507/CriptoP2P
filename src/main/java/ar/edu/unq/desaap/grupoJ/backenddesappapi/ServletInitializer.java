package ar.edu.unq.desaap.grupoJ.backenddesappapi;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.CacheService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BackendDesappApiApplication.class);
	}

}
