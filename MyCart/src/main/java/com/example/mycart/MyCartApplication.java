package com.example.mycart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MyCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyCartApplication.class, args);
	}

}
