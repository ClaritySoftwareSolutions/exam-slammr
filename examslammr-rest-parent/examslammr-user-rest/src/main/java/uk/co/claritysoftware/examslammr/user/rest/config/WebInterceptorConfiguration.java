package uk.co.claritysoftware.examslammr.user.rest.config;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.co.claritysoftware.examslammr.web.interceptor.ClearMdcInterceptor;
import uk.co.claritysoftware.examslammr.web.interceptor.IdentityIdMdcInterceptor;

/**
 * Spring Boot configuration class to wire in our web interceptors
 */
@Configuration
public class WebInterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IdentityIdMdcInterceptor()).order(HIGHEST_PRECEDENCE);
        registry.addInterceptor(new ClearMdcInterceptor()).order(LOWEST_PRECEDENCE);
    }
}
