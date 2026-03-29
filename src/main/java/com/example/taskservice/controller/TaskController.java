package com.example.taskservice.controller;

import com.example.taskservice.dto.request.AssignRequest;
import com.example.taskservice.dto.request.ChangeStatusRequest;
import com.example.taskservice.dto.request.TaskCreateRequest;
import com.example.taskservice.dto.response.TaskResponse;
import com.example.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public Page<TaskResponse> getAll(Pageable pageable) {
        return taskService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @PostMapping
    public TaskResponse create(@RequestBody TaskCreateRequest request) {
        return taskService.create(request);
    }

    @PostMapping("/{id}/assign")
    public TaskResponse assign(
            @PathVariable Long id,
            @RequestBody AssignRequest request
    ) {
        return taskService.assign(id, request.getUserId());
    }

    @PostMapping("/{id}/status")
    public TaskResponse changeStatus(
            @PathVariable Long id,
            @RequestBody ChangeStatusRequest request
    ) {
        return taskService.changeStatus(id, request.getStatus());
    }
}
