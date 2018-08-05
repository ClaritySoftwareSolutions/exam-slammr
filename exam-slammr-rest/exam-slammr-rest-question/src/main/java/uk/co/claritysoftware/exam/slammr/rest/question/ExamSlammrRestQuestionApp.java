package uk.co.claritysoftware.exam.slammr.rest.question;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Main Spring Boot bootstrapping class
 */
@SpringBootApplication
@RefreshScope
public class ExamSlammrRestQuestionApp {

	public static void main(String[] args) {
		SpringApplication.run(ExamSlammrRestQuestionApp.class, args);
	}
}
