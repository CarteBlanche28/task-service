package com.example.taskservice.kafka.producer;

import com.example.taskservice.kafka.event.TaskAssignedEvent;
import com.example.taskservice.kafka.event.TaskCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTaskCreated(TaskCreatedEvent event) {
        kafkaTemplate.send("task-created", event);
    }

    public void sendTaskAssigned(TaskAssignedEvent event) {
        kafkaTemplate.send("task-assigned", event);
    }
}
