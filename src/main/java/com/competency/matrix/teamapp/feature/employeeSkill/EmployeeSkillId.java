package com.competency.matrix.teamapp.feature.employeeSkill;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EmployeeSkillId implements Serializable {
    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "skill_id", nullable = false)
    private UUID skillId;
}
