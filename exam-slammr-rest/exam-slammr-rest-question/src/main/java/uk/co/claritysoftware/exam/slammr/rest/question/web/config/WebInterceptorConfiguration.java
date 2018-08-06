package uk.co.claritysoftware.exam.slammr.rest.question.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.co.claritysoftware.exam.slammr.web.interceptor.ClearMdcInterceptor;
import uk.co.claritysoftware.exam.slammr.web.interceptor.IdentityIdMdcInterceptor;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

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
