package com.competency.matrix.teamapp.skill;

import com.competency.matrix.teamapp.employeeSkill.EmployeeSkill;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @Column(name = "skillId")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "skill")
    @JsonBackReference
    //TODO: czy lepsza List czy Set
    private Set<EmployeeSkill> employeeSkills;
}
