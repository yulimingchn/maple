package com.dawnyu.maple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *  @author dawnyu
 *  开启定时任务支持
 */
@SpringBootApplication
@EnableScheduling
public class MapleBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleBlogApplication.class, args);
    }
}
