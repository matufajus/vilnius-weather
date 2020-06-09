package com.javatask.vilniusweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class VilniusWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(VilniusWeatherApplication.class, args);
	}

}
