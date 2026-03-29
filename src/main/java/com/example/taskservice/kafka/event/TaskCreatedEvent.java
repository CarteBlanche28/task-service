package com.example.taskservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskCreatedEvent {
    private Long taskId;
    private String name;
}
