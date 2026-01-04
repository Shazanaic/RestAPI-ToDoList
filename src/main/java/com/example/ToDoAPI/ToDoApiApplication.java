package com.example.ToDoAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ToDoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoApiApplication.class, args);
	}

}
