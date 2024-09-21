package com.example.Terriffic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TerrifficApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerrifficApplication.class, args);
	}

}
