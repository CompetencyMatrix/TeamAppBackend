package com.competency.matrix.teamapp.employeeSkill;

import com.competency.matrix.teamapp.employee.Employee;
import com.competency.matrix.teamapp.skill.Skill;
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
    @Id
    @Column(name = "eskill_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Enumerated(EnumType.STRING)
    private EmployeeSkillLevel employeeSkillLevel;

    public EmployeeSkill(Employee employee, Skill skill, EmployeeSkillLevel level) {
        this.employee = employee;
        this.skill = skill;
        this.employeeSkillLevel = level;
    }
}
