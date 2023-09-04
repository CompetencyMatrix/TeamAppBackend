package com.competency.matrix.teamapp.feature.employee;

import com.competency.matrix.teamapp.feature.employee.dto.EmployeeDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
@CrossOrigin(origins = {"http://localhost:4200"})
public class EmployeeController {
    private final EmployeeServiceInterface employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees(
            @RequestParam(required = false, name = "skills") List<String> requiredSkillsNames,
            @RequestParam(required = false, name = "projectId") UUID employeesCommonProjectId,
            @RequestParam(required = false, name = "name") String employeeName
    ) {
        if( employeeName != null) {
            return ResponseEntity.ok(employeeService.getEmployeesByName(employeeName));
        }
        if (requiredSkillsNames != null) {
            return ResponseEntity.ok(employeeService.getEmployeesBySkillsNames(requiredSkillsNames));
        }
        if (employeesCommonProjectId != null) {
            return ResponseEntity.ok(employeeService.getEmployeesByProjectId(employeesCommonProjectId));
        }
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @PostMapping
    public ResponseEntity<Object> addEmployee(@Valid @RequestBody EmployeeDto employee) {
        employeeService.addEmployee(employee);
        return ResponseEntity.created(URI.create(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable UUID id, @Valid @RequestBody EmployeeDto employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
