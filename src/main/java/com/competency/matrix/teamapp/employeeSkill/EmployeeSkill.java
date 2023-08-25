package com.competency.matrix.teamapp.employeeSkill;

import com.competency.matrix.teamapp.employee.Employee;
import com.competency.matrix.teamapp.skill.Skill;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeSkill {
    @EmbeddedId
    private EmployeeSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId")
    @MapsId("employeeId")
    @JsonBackReference
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "skillId")
    @MapsId("skillId")
    @JsonManagedReference
    private Skill skill;

    //TODO: Read about broad approach to using enums here
    @Enumerated(EnumType.ORDINAL)
    private EmployeeSkillLevel employeeSkillLevel;

}
