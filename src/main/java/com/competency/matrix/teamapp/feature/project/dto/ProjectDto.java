package com.competency.matrix.teamapp.feature.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ProjectDto(
        @JsonProperty("name")
        @NotBlank(message = "Name cannot be null or blank.")
        String name,

        @JsonProperty("startDate")
        @DateTimeFormat
        ZonedDateTime startDate,

        @JsonProperty("deadline")
        @DateTimeFormat
        ZonedDateTime deadline) {
}
