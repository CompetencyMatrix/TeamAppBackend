package com.competency.matrix.teamapp.feature.employeeSkill.dto;

import com.competency.matrix.teamapp.feature.employeeSkill.EmployeeSkillLevel;
import com.competency.matrix.teamapp.feature.skill.Skill;
import com.competency.matrix.teamapp.feature.skill.dto.SkillDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeSkillDto(
        @JsonProperty("skill")
        SkillDto skill,

        @JsonProperty("level")
        EmployeeSkillLevel level
) {
}
