package com.example.taskservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskAssignedEvent {
    private Long taskId;
    private Long userId;
}
