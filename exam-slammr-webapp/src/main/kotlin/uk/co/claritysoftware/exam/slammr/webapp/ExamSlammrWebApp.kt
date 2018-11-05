package uk.co.claritysoftware.exam.slammr.webapp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.context.config.annotation.RefreshScope

/**
 * Main Spring Boot bootstrapping class
 */
@SpringBootApplication
@RefreshScope
open class ExamSlammrWebApp

fun main(args: Array<String>) {
    SpringApplication.run(ExamSlammrWebApp::class.java, *args)
}