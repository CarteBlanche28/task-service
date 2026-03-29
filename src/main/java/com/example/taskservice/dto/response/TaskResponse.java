package com.example.taskservice.dto.response;

import com.example.taskservice.model.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private TaskStatus status;
    private Long executorId;
}
