package com.ozads.futsalnepal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FutsalnepalApplication {

	public static void main(String[] args) {
		SpringApplication.run(FutsalnepalApplication.class, args);
	}
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FutsalnepalApplication.class);
	}
}
