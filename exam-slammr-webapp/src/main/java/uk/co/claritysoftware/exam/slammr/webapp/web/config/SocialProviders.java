package uk.co.claritysoftware.exam.slammr.webapp.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.Value;

/**
 * A simple bean encapsulating which Social Providers are configured
 */
@Value
@Component
public class SocialProviders {

	private final boolean facebookConfigured;

	private final boolean twitterConfigured;

	private final boolean linkedinConfigured;

	@Autowired
	public SocialProviders(ApplicationContext applicationContext) {
		facebookConfigured = applicationContext.containsBean("facebook");
		twitterConfigured = applicationContext.containsBean("twitter");
		linkedinConfigured = applicationContext.containsBean("linkedin");
	}
}
