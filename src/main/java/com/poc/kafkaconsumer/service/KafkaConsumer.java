package com.poc.kafkaconsumer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    // group_id_01 is configured in consumerFactory
    @KafkaListener(topics = "my-topic")
    public void consume(String message) {
        System.out.println("Received Message: " + message);
    }
}

