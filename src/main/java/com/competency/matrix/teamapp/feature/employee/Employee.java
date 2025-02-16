package com.competency.matrix.teamapp.feature.employee;

import com.competency.matrix.teamapp.feature.employeeSkill.EmployeeSkill;
import com.competency.matrix.teamapp.feature.project.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Employee name cannot be blank.")
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must consists only from letters.")
    private String name;

    @NotBlank(message = "Employee surname cannot be blank.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Surname must consists only from letters.")
    private String surname;

    @Column(name = "hire_date")
    private ZonedDateTime hireDate;

    @ManyToOne
    private Employee manager;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeSkill> skills;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Project> projects;
}
