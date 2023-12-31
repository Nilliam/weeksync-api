package io.weeksync.weeksync.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Recurrence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private LocalDateTime createdAt;

    private LocalDate recurUntil;

    @Enumerated(EnumType.STRING)
    private RecurrenceType type;

    @JoinColumn
    @OneToOne
    private Task templateTask;

}
