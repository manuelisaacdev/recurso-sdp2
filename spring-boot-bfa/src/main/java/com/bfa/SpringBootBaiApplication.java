package com.bfa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bfa.storage.StorageService;

@SpringBootApplication
public class SpringBootBaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBaiApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initStorage(StorageService storageService) {
		return args -> storageService.init();
	}
	
}
