package com.lass.categories;

import com.lass.EnableLassMQTT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLassMQTT
public class CategoriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoriesApplication.class, args);
	}

}
