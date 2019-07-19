package com.dawn.maple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *  @author dawnyu
 *  开启定时任务支持
 */
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
@MapperScan(basePackages = "com.dawn.maple.mapper")
public class MapleBlog {

    public static void main(String[] args) {
        SpringApplication.run(MapleBlog.class, args);
    }

}
