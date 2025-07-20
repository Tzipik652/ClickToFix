package com.technicians.clicktofix;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ClicktofixApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load(); 
        System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
        System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));
        
		SpringApplication.run(ClicktofixApplication.class, args);
	}
	
	@Bean
	public ModelMapper getMapper(){
		return new ModelMapper();
	}
}
