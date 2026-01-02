package com.example.ToDoAPI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE;

    @JsonCreator
    public static TaskStatus from(String value) {
        return TaskStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toJson() {
        return name();
    }
}
