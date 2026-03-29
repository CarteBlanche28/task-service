package com.example.taskservice.service;

import com.example.taskservice.dto.request.TaskCreateRequest;
import com.example.taskservice.dto.response.TaskResponse;
import com.example.taskservice.kafka.event.TaskAssignedEvent;
import com.example.taskservice.kafka.event.TaskCreatedEvent;
import com.example.taskservice.kafka.producer.KafkaProducerService;
import com.example.taskservice.model.entity.Task;
import com.example.taskservice.model.entity.User;
import com.example.taskservice.model.enums.TaskStatus;
import com.example.taskservice.repository.TaskRepository;
import com.example.taskservice.repository.UserRepository;
import com.example.taskservice.exception.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final KafkaProducerService kafkaProducer;

    public Page<TaskResponse> getAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public TaskResponse getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Task not found", "TASK_NOT_FOUND"));

        return toResponse(task);
    }

    public TaskResponse create(TaskCreateRequest request) {
        Task task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.NEW);

        task = taskRepository.save(task);

        kafkaProducer.sendTaskCreated(
                new TaskCreatedEvent(task.getId(), task.getName())
        );

        return toResponse(task);
    }

    public TaskResponse assign(Long taskId, Long userId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new NotFoundException("Task not found", "TASK_NOT_FOUND"));

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException("User not found", "USER_NOT_FOUND"));

        task.setExecutor(user);
        taskRepository.save(task);

        kafkaProducer.sendTaskAssigned(
                new TaskAssignedEvent(taskId, userId)
        );

        return toResponse(task);
    }

    public TaskResponse changeStatus(Long taskId, TaskStatus status) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new NotFoundException("Task not found", "TASK_NOT_FOUND"));

        if (status == null) {
            throw new BadRequestException("Status must not be null", "INVALID_STATUS");
        }

        task.setStatus(status);
        taskRepository.save(task);

        return toResponse(task);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getExecutor() != null ? task.getExecutor().getId() : null
        );
    }
}
