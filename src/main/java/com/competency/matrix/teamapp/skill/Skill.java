package com.competency.matrix.teamapp.skill;

import com.competency.matrix.teamapp.employeeSkill.EmployeeSkill;
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
    private String name;

    @OneToMany(mappedBy = "skill")
    //TODO: czy lepsza List czy Set
    private Set<EmployeeSkill> employeeSkills;
}
