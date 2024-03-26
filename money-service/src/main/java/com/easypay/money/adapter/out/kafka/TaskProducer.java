package com.easypay.money.adapter.out.kafka;

import com.easypay.common.RechargingMoneyTask;
import com.easypay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TaskProducer implements SendRechargingMoneyTaskPort {
    private final KafkaProducer<String, String> producer;

    private final String topic;

    public TaskProducer(
            @Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
            @Value("${task.topic}") String topic
    ) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    @Override
    public void sendRechargingMoneyTaskPort(RechargingMoneyTask rechargingMoneyTask) {
        this.sendMessage(rechargingMoneyTask.getTaskId(), rechargingMoneyTask);
    }

    public void sendMessage(String key, RechargingMoneyTask rechargingMoneyTask) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStringToProduce;
        // jsonString
        try {
            jsonStringToProduce = mapper.writeValueAsString(rechargingMoneyTask);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, jsonStringToProduce);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                // System.out.println("Message sent successfully. Offset: " + metadata.offset());
            } else {
                exception.printStackTrace();
                // System.err.println("Failed to send message: " + exception.getMessage());
            }
        });
    }
}
