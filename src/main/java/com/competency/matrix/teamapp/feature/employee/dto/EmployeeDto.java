package com.competency.matrix.teamapp.feature.employee.dto;

import com.competency.matrix.teamapp.feature.employeeSkill.dto.EmployeeSkillDto;
import com.competency.matrix.teamapp.feature.project.dto.ProjectDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record EmployeeDto(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("name")
        @NotBlank(message = "Employee name cannot be blank.")
        @Size(min = 1, max = 50)
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must consists only from letters.")
        String name,

        @JsonProperty("surname")
        @NotBlank(message = "Employee surname cannot be blank.")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Surname must consists only from letters.")
        String surname,

        @JsonProperty("hire_date")
        ZonedDateTime hireDate,

        @JsonProperty("manager")
        UUID managerId,

        @JsonProperty("skills")
        Set<EmployeeSkillDto> skills,

        @JsonProperty("projects")
        List<ProjectDto> projects
) {
}
