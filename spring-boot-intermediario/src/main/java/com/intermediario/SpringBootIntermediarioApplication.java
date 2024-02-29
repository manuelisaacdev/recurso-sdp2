package com.intermediario;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.intermediario.storage.StorageService;

@SpringBootApplication
public class SpringBootIntermediarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootIntermediarioApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initStorage(StorageService storageService) {
		return args -> storageService.init();
	}

}
