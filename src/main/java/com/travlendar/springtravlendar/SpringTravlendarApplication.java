package com.travlendar.springtravlendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration (exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class SpringTravlendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTravlendarApplication.class, args);
	}
}
