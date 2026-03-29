package com.example.taskservice.dto.request;

import com.example.taskservice.model.enums.TaskStatus;
import lombok.Data;

@Data
public class ChangeStatusRequest {
    private TaskStatus status;
}
