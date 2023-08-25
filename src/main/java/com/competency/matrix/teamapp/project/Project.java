package com.competency.matrix.teamapp.project;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @Column(name = "projectId")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime deadline;

}
