package uk.co.claritysoftware.exam.slammr.webapp.web.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.social.security.SpringSocialConfigurer

/**
 * Spring Security config class to setup web security concerns
 */
@Configuration
@EnableWebSecurity
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {

	override fun configure(http: HttpSecurity) {
		// @formatter:off
		http
			.authorizeRequests()
			.antMatchers("/user-profile").authenticated()
			.antMatchers(HttpMethod.GET, "/question/new").authenticated()
			.antMatchers(HttpMethod.POST, "/question").authenticated()
			.antMatchers("/**").permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/")
			.deleteCookies("JSESSIONID")
		.and()
			.rememberMe()
		.and()
			.apply(SpringSocialConfigurer())
 		// @formatter:on
	}

}
