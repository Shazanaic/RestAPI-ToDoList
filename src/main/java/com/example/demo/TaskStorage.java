package com.example.demo;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

@Component
public class TaskStorage {

    private final Map<Long, Task> tasks = new HashMap<>();
    private long nextId = 1;

    public ArrayList<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public Task save(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        return task;
    }

    public Task update(Long id, Task updatedTask) {
        Task existing = tasks.get(id);
        if (existing == null) {
            return null;
        }

        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setStatus(updatedTask.getStatus());

        return existing;
    }

    public boolean delete(Long id) {
        return tasks.remove(id) != null;
    }
}

