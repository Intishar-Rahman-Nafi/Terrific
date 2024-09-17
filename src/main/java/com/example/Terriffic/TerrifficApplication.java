package com.example.Terriffic;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TerrifficApplication {

	public static void main(String[] args) {
		// Set application properties from .env file
		Dotenv dotenv = DotenvSingleton.getInstance();
		String DB_URL = dotenv.get("DB_URL");
		String DB_USERNAME = dotenv.get("DB_USERNAME");
		String DB_PASSWORD = dotenv.get("DB_PASSWORD");

		if (DB_URL == null || DB_USERNAME == null || DB_PASSWORD == null) {
			System.out.println("Error: .env file not found or missing required fields");
			System.exit(1);
		}

		System.setProperty("spring.datasource.url", DB_URL);
		System.setProperty("spring.datasource.username", DB_USERNAME);
		System.setProperty("spring.datasource.password", DB_PASSWORD);

		SpringApplication.run(TerrifficApplication.class, args);
	}

}
