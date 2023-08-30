package com.competency.matrix.teamapp.feature.employee;

import com.competency.matrix.teamapp.feature.employee.dto.EmployeeDto;
import com.competency.matrix.teamapp.feature.skill.dto.SkillDto;

import java.util.List;
import java.util.UUID;

public interface EmployeeServiceInterface {
    List<EmployeeDto> getEmployees();

    List<EmployeeDto> getEmployeesBySkillsNames(List<String> requiredSkillsNames);

    List<EmployeeDto> getEmployeesByProjectId(UUID employeesCommonProjectId);

    void addEmployees(List<EmployeeDto> employees);

    void addEmployee(EmployeeDto employee);

    EmployeeDto updateEmployee(UUID employeeId, EmployeeDto employee);

    EmployeeDto getEmployee(UUID employeeId);

    void deleteEmployee(UUID employeeId);

    void addSkillsToEmployee(EmployeeDto employee, List<SkillDto> skills);
}
