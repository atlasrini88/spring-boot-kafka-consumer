package com.poc.kafkaconsumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "my-topic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}

