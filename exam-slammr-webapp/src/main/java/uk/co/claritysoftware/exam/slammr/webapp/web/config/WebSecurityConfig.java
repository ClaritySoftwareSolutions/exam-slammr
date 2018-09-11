package uk.co.claritysoftware.exam.slammr.webapp.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Spring Security config class to setup web security concerns
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
				.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/signup").anonymous()
				.antMatchers("/user-profile").authenticated()
			.and()
				.logout()
					.logoutSuccessUrl("/")
					.deleteCookies("JSESSIONID")
			.and()
				.rememberMe()
			.and()
				.apply(new SpringSocialConfigurer());
		// @formatter:on
	}

}
