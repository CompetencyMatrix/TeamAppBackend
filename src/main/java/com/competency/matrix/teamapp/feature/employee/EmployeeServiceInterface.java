package com.competency.matrix.teamapp.feature.employee;

import com.competency.matrix.teamapp.feature.employee.dto.EmployeeDto;
import com.competency.matrix.teamapp.feature.skill.Skill;

import java.util.List;
import java.util.UUID;

public interface EmployeeServiceInterface {
    List<EmployeeDto> getEmployees(List<String> requiredSkillsNames, UUID employeesCommonProjectId);

    void addEmployees(List<EmployeeDto> employees);

    void addEmployee(EmployeeDto employee);

    EmployeeDto updateEmployee(UUID employeeId, EmployeeDto employee);

    EmployeeDto getEmployee(UUID employeeId);

    void deleteEmployee(UUID employeeId);

    //TODO: Dto tutaj
    void addSkillsToEmployee(Employee employee, List<Skill> skills);
}
