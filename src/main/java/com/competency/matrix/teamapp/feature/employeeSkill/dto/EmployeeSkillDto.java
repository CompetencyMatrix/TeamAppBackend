package com.competency.matrix.teamapp.feature.employeeSkill.dto;

import com.competency.matrix.teamapp.feature.skill.dto.SkillDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record EmployeeSkillDto(
        @JsonProperty("skill")
        SkillDto skill,

        @JsonProperty("employee")
        UUID employeeId,

        @JsonProperty("level")
        String level
) {
}
