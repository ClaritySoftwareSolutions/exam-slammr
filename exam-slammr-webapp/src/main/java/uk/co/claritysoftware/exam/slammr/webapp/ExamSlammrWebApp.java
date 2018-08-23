package uk.co.claritysoftware.exam.slammr.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Main Spring Boot bootstrapping class
 */
@SpringBootApplication
@RefreshScope
public class ExamSlammrWebApp {

	public static void main(String[] args) {
		SpringApplication.run(ExamSlammrWebApp.class, args);
	}
}
