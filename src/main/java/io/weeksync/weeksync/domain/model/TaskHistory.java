package io.weeksync.weeksync.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @JoinColumn
    @ManyToOne
    private Task task;

    private String title;

    private String description;

    private TaskStatus status;

    private LocalDateTime dateTime;

}
