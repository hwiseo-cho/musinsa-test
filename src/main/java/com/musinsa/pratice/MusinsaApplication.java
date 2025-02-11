package com.musinsa.pratice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MusinsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusinsaApplication.class, args);
	}

}
