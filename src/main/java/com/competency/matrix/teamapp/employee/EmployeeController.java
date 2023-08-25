package com.competency.matrix.teamapp.employee;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees(@RequestParam(required = false, name = "skills") List<String> requiredSkillsNames, @RequestParam(required = false, name = "projectId") String employeesCommonProjectId ){
        return ResponseEntity.ok(employeeService.getEmployees(requiredSkillsNames, employeesCommonProjectId));
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @Valid @RequestBody Employee employee) {
        employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok().build();
    }
}
