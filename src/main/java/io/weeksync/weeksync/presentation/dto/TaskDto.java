package io.weeksync.weeksync.presentation.dto;

import io.weeksync.weeksync.domain.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TaskDto {

    UUID getId();

    String getTitle();

    String getDescription();

    LocalDateTime getCreatedAt();

    TaskStatus getStatus();

}
