package uk.co.claritysoftware.exam.slammr.webapp.web.config

import lombok.Value
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

/**
 * A simple bean encapsulating which Social Providers are configured
 */
@Value
@Component
class SocialProviders @Autowired
constructor(applicationContext: ApplicationContext) {

	val isFacebookConfigured: Boolean = applicationContext.containsBean("facebook")

	val isTwitterConfigured: Boolean = applicationContext.containsBean("twitter")

	val isLinkedinConfigured: Boolean = applicationContext.containsBean("linkedin")
}
