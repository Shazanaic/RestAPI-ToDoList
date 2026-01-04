package com.example.ToDoAPI;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Cacheable("tasks")
    public List<TaskResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Cacheable(value = "taskById", key = "#id")
    public Optional<TaskResponseDto> findById(String id) {
        return repository.findById(id)
                .map(this::toResponseDto);
    }

    @CacheEvict(value = {"tasks", "taskById"}, allEntries = true)
    public TaskResponseDto create(TaskCreateDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(
                dto.getStatus() != null ? dto.getStatus() : TaskStatus.TODO
        );

        Task saved = repository.save(task);
        return toResponseDto(saved);
    }

    @CacheEvict(value = {"tasks", "taskById"}, allEntries = true)
    public Optional<TaskResponseDto> update(String id, TaskUpdateDto dto) {
        return repository.findById(id).map(task -> {
            task.setTitle(dto.getTitle());
            task.setDescription(dto.getDescription());
            task.setStatus(dto.getStatus());

            Task saved = repository.save(task);
            return toResponseDto(saved);
        });
    }

    @CacheEvict(value = {"tasks", "taskById"}, allEntries = true)
    public boolean deleteById(String id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
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
