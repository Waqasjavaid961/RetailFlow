package com.retailflow.retailflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// Main entry point of the RetailFlow application
// @SpringBootApplication enables auto-configuration, component scan, and configuration
// @EnableJpaAuditing allows automatic tracking of createdAt and modifiedAt fields in BaseEntity
@SpringBootApplication
@EnableJpaAuditing
public class RetailflowApplication {
	public static void main(String[] args) {
		SpringApplication.run(RetailflowApplication.class, args);
	}

}
