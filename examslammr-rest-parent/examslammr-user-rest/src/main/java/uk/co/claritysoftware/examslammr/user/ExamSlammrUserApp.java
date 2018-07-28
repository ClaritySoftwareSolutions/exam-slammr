package uk.co.claritysoftware.examslammr.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Main Spring Boot bootstrapping class
 */
@SpringBootApplication
@RefreshScope
public class ExamSlammrUserApp {

	public static void main(String[] args) {
		SpringApplication.run(ExamSlammrUserApp.class, args);
	}
}
