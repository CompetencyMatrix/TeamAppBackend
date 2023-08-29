package com.competency.matrix.teamapp.feature.skill.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record SkillDto(
        @JsonProperty("id")
        UUID id,

        @NotBlank(message = "Name cannot be null or blank.")
        @JsonProperty("name")
        String name
) {}
