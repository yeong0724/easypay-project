package com.easypay.common;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class LoggingProducer {
    private final KafkaProducer<String, String> kafkaProducer;

    private final String topic;

    // 각 Service 에 따라 서로 다른 환경변수 값
    public LoggingProducer(
            @Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
            @Value("${logging.topic}") String topic
    ) {
        // Producer Initialization
        Properties properties = new Properties();

        // kafka:29092
        properties.put("bootstrap.servers", bootstrapServers);

        /**
         * kafka cluster 에 모든 데이터를 key:value 쌍으로 produce 한다.
         * @serialize 란?
         * - produce 를 함에 있어서 각각의 key 와 value 를 어느 data 로써 판별을 하고 serialize 할 것 인가?
         * - produce 란 작업은 결국 각각의 Service 에서 Kafka Cluster 라는 External System 으로 데이터가 옮겨 가는것이기 때문에
         *   Kafka 가 해당 데이터를 "어떻게 간주 해서 kafka cluster 의 특정 broker 안에 Produce 된 데이터를 가지고 있을 것인가?" 를 정의하는 것이다.
         */
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.kafkaProducer = new KafkaProducer<>(properties);
        this.topic = topic;
    }

    public void sendMessage(String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception == null) {
                // 성공
                // System.out.println("Message sent successfully. Offset: " + metadata.offset());
            } else {
                // 실패
                exception.printStackTrace();
                // System.err.println("Failed to send message: " + exception.getMessage());
            }
        });
    }
}
