package com.technicians.clicktofix;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.technicians.clicktofix.dto.CustomerDto;
import com.technicians.clicktofix.dto.TechnicianDto;
import com.technicians.clicktofix.model.Customer;
import com.technicians.clicktofix.model.Technician;

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
		ModelMapper mapper = new ModelMapper();

		mapper.typeMap(CustomerDto.class, Customer.class)
			.addMapping(CustomerDto::getPassword, Customer::setPasswordHash);
		mapper.typeMap(TechnicianDto.class, Technician.class)
			.addMapping(TechnicianDto::getPassword, Technician::setPasswordHash);
		return mapper;
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
