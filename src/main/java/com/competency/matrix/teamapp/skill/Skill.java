package com.competency.matrix.teamapp.skill;

import com.competency.matrix.teamapp.employeeSkill.EmployeeSkill;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
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
