package com.easypay.money.adapter.in.kafka;

import com.easypay.common.CountDownLatchManager;
import com.easypay.common.LoggingProducer;
import com.easypay.common.RechargingMoneyTask;
import com.easypay.common.SubTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Component
public class RechargingMoneyResultConsumer {
    private final KafkaConsumer<String, String> consumer;

    private final LoggingProducer loggingProducer;

    @NotNull
    private final CountDownLatchManager countDownLatchManager;
    public RechargingMoneyResultConsumer(
            @Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
            @Value("${task.result.topic}")String topic,
            LoggingProducer loggingProducer,
            CountDownLatchManager countDownLatchManager
    ) {
        this.loggingProducer = loggingProducer;
        this.countDownLatchManager = countDownLatchManager;

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "my-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        Thread consumerThread = new Thread(() -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println("Received message: " + record.key()  + " / "+  record.value());
                        // record: RechargingMoneyTask, ( all subtask is don)


                        RechargingMoneyTask rechargingMoneyTask;
                        try {
                            rechargingMoneyTask = mapper.readValue(record.value(), RechargingMoneyTask.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        List<SubTask> subTaskList = rechargingMoneyTask.getSubTaskList();

                        boolean taskResult = true;
                        // validation Membership-service
                        // validation Banking-service
                        for (SubTask subTask : subTaskList) {
                            // 한번만 실패해도 실패한 task 로 간주.
                            if (subTask.getStatus().equals("fail")) {
                                taskResult = false;
                                break;
                            }
                        }

                        // 모두 정상적으로 성공했다면
                        if (taskResult) {
                            this.loggingProducer.sendMessage(rechargingMoneyTask.getTaskId(), "task success");
                            this.countDownLatchManager.setDataForKey(rechargingMoneyTask.getTaskId(), "success");
                        } else{
                            this.loggingProducer.sendMessage(rechargingMoneyTask.getTaskId(), "task failed");
                            this.countDownLatchManager.setDataForKey(rechargingMoneyTask.getTaskId(), "failed");
                        }

                        // Service 에서 await 걸린 thread 가 풀리게 된다.
                        this.countDownLatchManager.getCountDownLatch(rechargingMoneyTask.getTaskId()).countDown();
                    }
                }
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();
    }
}
