package uk.co.claritysoftware.exam.slammr.webapp.web.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

/**
 * Spring Security config class to setup web mvc concerns
 */
@Configuration
open class WebMvcConfig {

	@Bean
	open fun messageSource(): MessageSource {
		val messageSource = ReloadableResourceBundleMessageSource()
		messageSource.setBasename("classpath:messages")
		messageSource.setDefaultEncoding("UTF-8")
		return messageSource
	}

	@Bean
	open fun validator(): LocalValidatorFactoryBean {
		val bean = LocalValidatorFactoryBean()
		bean.setValidationMessageSource(messageSource())
		return bean
	}

}
