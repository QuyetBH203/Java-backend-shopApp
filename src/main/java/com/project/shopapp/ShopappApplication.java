package com.project.shopapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class ShopappApplication {

	//http://localhost:8088/swagger-ui/index.html

	public static void main(String[] args) {
		SpringApplication.run(ShopappApplication.class, args);
	}

}
