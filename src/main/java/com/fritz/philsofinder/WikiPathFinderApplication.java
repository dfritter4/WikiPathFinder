package com.fritz.philsofinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WikiPathFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WikiPathFinderApplication.class, args);
	}
}
