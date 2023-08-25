package com.competency.matrix.teamapp.employee;

import com.competency.matrix.teamapp.employeeSkill.EmployeeSkill;
import com.competency.matrix.teamapp.project.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @Column(name = "employeeId")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Employee name cannot be blank.")
    private String name;

    @NotBlank(message = "Employee surname cannot be blank.")
    private String surname;

    @Column(name = "hire_date")
    private ZonedDateTime hireDate;

    @ManyToOne
    private Employee manager;

    //MappedBy added, because of OpenApi specification - there are possible calls that require finding employees, that have particular skill - search can go in this direction
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    //JsonIgnore dodane, zeby mozna bylo wykonac POST(JsonManagedReference to uniemozliwia, ale jest potrzebna by uniknac Reccursion error w jsonie) - zostanie zmienione w ramach migracji do DTO
    @JsonIgnore
    private Set<EmployeeSkill> skills;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Project> projects;
}
