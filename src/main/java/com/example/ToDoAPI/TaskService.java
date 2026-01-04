package com.example.ToDoAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private static final Logger log =
            LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Cacheable("tasks")
    public List<TaskResponseDto> findAll() {
        log.info("Fetching all tasks");
        return repository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Cacheable(value = "taskById", key = "#id")
    public Optional<TaskResponseDto> findById(String id) {
        log.info("Fetching task by id={}", id);

        Optional<TaskResponseDto> result = repository.findById(id)
                .map(this::toResponseDto);

        if (result.isEmpty()) {
            log.warn("Task not found id={}", id);
        }

        return result;
    }

    @CacheEvict(value = {"tasks", "taskById"}, allEntries = true)
    public TaskResponseDto create(TaskCreateDto dto) {
        log.info("Creating new task with title='{}'", dto.getTitle());

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(
                dto.getStatus() != null ? dto.getStatus() : TaskStatus.TODO
        );

        Task saved = repository.save(task);

        log.info("Task created id={}", saved.getId());

        return toResponseDto(saved);
    }

    @CacheEvict(value = {"tasks", "taskById"}, allEntries = true)
    public Optional<TaskResponseDto> update(String id, TaskUpdateDto dto) {
        log.info("Updating task id={}", id);

        return repository.findById(id).map(task -> {
            task.setTitle(dto.getTitle());
            task.setDescription(dto.getDescription());
            task.setStatus(dto.getStatus());

            Task saved = repository.save(task);

            log.info("Task updated id={}", saved.getId());

            return toResponseDto(saved);
        });
    }

    @CacheEvict(value = {"tasks", "taskById"}, allEntries = true)
    public boolean deleteById(String id) {
        log.info("Deleting task id={}", id);

        if (!repository.existsById(id)) {
            log.warn("Delete failed, task not found id={}", id);
            return false;
        }

        repository.deleteById(id);
        log.info("Task deleted id={}", id);
        return true;
    }

    private TaskResponseDto toResponseDto(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }
}
