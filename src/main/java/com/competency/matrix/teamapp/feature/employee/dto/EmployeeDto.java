package com.competency.matrix.teamapp.feature.employee.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeDto(
        //TODO
        @Size(min = 1, max = 50)
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must consists only from letters.")
        String name
) {
}
