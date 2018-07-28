package uk.co.claritysoftware.examslammr.cloud.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Main Spring Boot Cloud Config Server bootstrapping class
 */
@SpringBootApplication
@EnableConfigServer
public class ExamSlammrCloudConfigServer {

	public static void main(String[] args) {
		SpringApplication.run(ExamSlammrCloudConfigServer.class, args);
	}
}
