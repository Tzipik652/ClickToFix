package com.technicians.clicktofix;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClicktofixApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClicktofixApplication.class, args);
	}
	
	@Bean
	public ModelMapper getMapper(){
		return new ModelMapper();
	}
}
