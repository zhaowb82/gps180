package com.gps.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gps.generator.dao")
public class GpsGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpsGeneratorApplication.class, args);
	}
}
