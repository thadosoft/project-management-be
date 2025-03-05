package com.example.projectmanagementbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectManagementBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementBeApplication.class, args);
	}
}
