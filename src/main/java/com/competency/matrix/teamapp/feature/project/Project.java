package com.competency.matrix.teamapp.feature.project;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    @NotBlank(message = "Name cannot be null or blank.")
    private String name;

    @DateTimeFormat
    private ZonedDateTime startDate;

    @DateTimeFormat
    private ZonedDateTime deadline;
}
