package com.example.txrole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TxRoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TxRoleApplication.class, args);
	}

}

