package config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	protected String getDispatcherWebApplicationContextSuffix() {
		return "springmvc";
	}
}
