package com.gps.gate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class GpsGateApplication {

    public static void main(String[] args) {
        SpringApplication.run(GpsGateApplication.class, args);
        log.info("***启动成功***");
    }

    @Bean
    public AppApplicationListener appApplicationListener() {
        return new AppApplicationListener();
    }
}