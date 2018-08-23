package uk.co.claritysoftware.exam.slammr.rest.user.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import uk.co.claritysoftware.exam.slammr.web.interceptor.ClearMdcInterceptor;
import uk.co.claritysoftware.exam.slammr.web.interceptor.IdentityIdMdcInterceptor;

/**
 * Spring Boot configuration class to wire in our web interceptors
 */
@Configuration
public class WebInterceptorConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new IdentityIdMdcInterceptor());
		registry.addInterceptor(new ClearMdcInterceptor());
	}
}
