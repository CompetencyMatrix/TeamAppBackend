package com.competency.matrix.teamapp.feature.skill.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record SkillDto(
        @NotBlank(message = "Name cannot be null or blank.")
        @JsonProperty("name")
        String name) {}
