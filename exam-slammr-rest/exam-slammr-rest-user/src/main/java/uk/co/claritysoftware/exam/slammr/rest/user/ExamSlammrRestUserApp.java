package uk.co.claritysoftware.exam.slammr.rest.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Main Spring Boot bootstrapping class
 */
@SpringBootApplication
@RefreshScope
public class ExamSlammrRestUserApp {

	public static void main(String[] args) {
		SpringApplication.run(ExamSlammrRestUserApp.class, args);
	}
}
