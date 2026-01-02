package com.example.ToDoAPI;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<TaskResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public TaskResponseDto create(TaskCreateDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());

        Task saved = repository.save(task);
        return toResponseDto(saved);
    }

    public Optional<TaskResponseDto> findById(String id) {
        return repository.findById(id)
                .map(this::toResponseDto);
    }

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
