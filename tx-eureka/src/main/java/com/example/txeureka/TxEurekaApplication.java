package com.example.txeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TxEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TxEurekaApplication.class, args);
	}

}

