package com.poc.kafkaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootKafkaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaConsumerApplication.class, args);
		System.out.println("Welcome to Spring Boot with Kafka application which receives messages from Kafka ecosystem");
	}

}
