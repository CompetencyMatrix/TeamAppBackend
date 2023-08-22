package com.competency.matrix.teamapp.employee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String surname;
    private ZonedDateTime hireDate;
//TODO: add skills projects and avatar
    @ManyToOne
    private Employee manager;

    @ElementCollection
    private List<String> skills;
}
