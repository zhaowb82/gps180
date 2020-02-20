
package com.gps.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.gps.api",
		"com.gps.db",
		"com.gps.websocket",
		"com.muheda.notice"
})
@MapperScan({"com.gps.api.modules.*.dao", "com.gps.db.dao"})
public class GpsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpsApiApplication.class, args);
	}

}