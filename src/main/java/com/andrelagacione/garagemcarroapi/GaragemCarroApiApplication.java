package com.andrelagacione.garagemcarroapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GaragemCarroApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GaragemCarroApiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {}
}