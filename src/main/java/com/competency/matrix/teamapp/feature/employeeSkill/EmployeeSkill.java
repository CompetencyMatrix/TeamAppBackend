package com.competency.matrix.teamapp.feature.employeeSkill;

import com.competency.matrix.teamapp.feature.employee.Employee;
import com.competency.matrix.teamapp.feature.skill.Skill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeSkill {
    @EmbeddedId
    private EmployeeSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("skillId")
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Enumerated(EnumType.STRING)
    private EmployeeSkillLevel level;

    public EmployeeSkill(Employee employee, Skill skill, EmployeeSkillLevel level) {
        this.employee = employee;
        this.skill = skill;
        this.level = level;
        this.id = new EmployeeSkillId(employee.getId(), skill.getId());
    }

    public void setEmployee( Employee employee) {
        this.employee = employee;
        if (this.id == null) {
            this.id = new EmployeeSkillId();
        }
        this.id.setEmployeeId(employee.getId());
    }

    public void setSkill( Skill skill ) {
        this.skill = skill;
        if (this.id == null) {
            this.id = new EmployeeSkillId();
        }
        this.id.setSkillId(skill.getId());
    }
}
