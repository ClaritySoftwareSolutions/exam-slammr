package uk.co.claritysoftware.exam.slammr.user.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uk.co.claritysoftware.exam.slammr.security.authentication.UnauthorisedAuthenticationEntryPoint;
import uk.co.claritysoftware.exam.slammr.security.filter.IdentityIdHeaderFilter;

import javax.servlet.Filter;

/**
 * Spring Boot configuration for web security concerns
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .anonymous().disable()
                    .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())
                .and()
                    .addFilterAt(identityIdHeaderFilter(), BasicAuthenticationFilter.class);

        // @formatter:on
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return new UnauthorisedAuthenticationEntryPoint();
    }

    @Bean
    public Filter identityIdHeaderFilter() {
        return new IdentityIdHeaderFilter();
    }
}
