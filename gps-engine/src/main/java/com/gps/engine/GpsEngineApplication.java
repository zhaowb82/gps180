package com.gps.engine;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.gps.db.config.DruidConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {
        "com.gps.engine",
        "com.gps.db"
}, exclude = {
//        DruidDataSourceAutoConfigure.class,
//        KafkaAutoConfiguration.class
})
@MapperScan("com.gps.db.dao")
public class GpsEngineApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(GpsEngineApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
