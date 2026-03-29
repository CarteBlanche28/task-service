package com.example.taskservice.dto.request;

import lombok.Data;

@Data
public class TaskCreateRequest {
    private String name;
    private String description;
}
