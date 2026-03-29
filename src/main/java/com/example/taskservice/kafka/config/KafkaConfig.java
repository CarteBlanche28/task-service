package com.example.taskservice.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic taskCreatedTopic() {
        return TopicBuilder.name("task-created")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic taskAssignedTopic() {
        return TopicBuilder.name("task-assigned")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
