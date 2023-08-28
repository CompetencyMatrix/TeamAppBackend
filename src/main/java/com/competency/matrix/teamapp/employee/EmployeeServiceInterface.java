package com.competency.matrix.teamapp.employee;

import com.competency.matrix.teamapp.skill.Skill;

import java.util.List;
import java.util.UUID;

public interface EmployeeServiceInterface {
    List<Employee> getEmployees(List<String> requiredSkillsNames, UUID employeesCommonProjectId);

    void addEmployees(List<Employee> employees);

    void addEmployee(Employee employee);

    Employee updateEmployee(UUID employeeId, Employee employee);

    Employee getEmployee(UUID employeeId);

    void deleteEmployee(UUID employeeId);

    void addSkillsToEmployee(Employee employee, List<Skill> skills);
}
