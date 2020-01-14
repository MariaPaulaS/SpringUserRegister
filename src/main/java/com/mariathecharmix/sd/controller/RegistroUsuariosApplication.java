package com.mariathecharmix.sd.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.mariathecharmix.sd"})
public class RegistroUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistroUsuariosApplication.class, args);
	}

}
