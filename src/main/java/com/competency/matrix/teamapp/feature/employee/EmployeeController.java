package com.competency.matrix.teamapp.feature.employee;

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
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees(@RequestParam(required = false, name = "skills") List<String> requiredSkillsNames, @RequestParam(required = false, name = "projectId") UUID employeesCommonProjectId) {
        return ResponseEntity.ok(employeeService.getEmployees(requiredSkillsNames, employeesCommonProjectId));
    }

    @PostMapping
    public ResponseEntity<Object> addEmployee(@Valid @RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return ResponseEntity.created(URI.create(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable UUID id, @Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
