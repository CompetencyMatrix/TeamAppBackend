package com.competency.matrix.teamapp.employeeSkill;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeSkillId implements Serializable {
    private String employeeId;
    private String skillId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeSkillId)) return false;
        EmployeeSkillId that = (EmployeeSkillId) o;
        return getEmployeeId().equals(that.getEmployeeId()) && getSkillId().equals(that.getSkillId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployeeId(), getSkillId());
    }
}
