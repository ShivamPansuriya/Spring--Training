package com.example.jpahibernate;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class JpahibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpahibernateApplication.class, args);

		// print all beans

	}

}
