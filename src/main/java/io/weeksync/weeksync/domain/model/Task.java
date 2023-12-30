package io.weeksync.weeksync.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @JoinColumn
    @ManyToOne
    private Recurrence recurrence;

    @JoinColumn
    @ManyToOne
    private Account account;

}
