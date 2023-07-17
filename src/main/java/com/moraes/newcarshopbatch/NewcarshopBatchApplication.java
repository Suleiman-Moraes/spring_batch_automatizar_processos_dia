package com.moraes.newcarshopbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewcarshopBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewcarshopBatchApplication.class, args);
	}

}
