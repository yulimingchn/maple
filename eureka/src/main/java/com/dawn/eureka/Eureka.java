package com.dawn.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author dawn
 */
@EnableEurekaServer
@SpringBootApplication
public class Eureka {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Eureka.class).run(args);
	}

}
