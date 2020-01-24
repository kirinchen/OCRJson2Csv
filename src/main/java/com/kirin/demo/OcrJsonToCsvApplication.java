package com.kirin.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@SpringBootApplication
public class OcrJsonToCsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(OcrJsonToCsvApplication.class, args);
	}
	
	  @Bean
	  public ObjectMapper ObjectMapper(){
	   ObjectMapper objectMapper=new ObjectMapper();
	   return objectMapper;
	  }

}
