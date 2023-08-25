package com.competency.matrix.teamapp.employee;

import com.competency.matrix.teamapp.skill.Skill;

import java.util.List;

public interface EmployeeServiceInterface {
    List<Employee> getEmployees(List<String> requiredSkillsNames, String employeesCommonProjectId);
    void addEmployees(List<Employee> employees);
    void addEmployee(Employee employee);
    Employee updateEmployee(String pathEmployeeId, Employee employee);
    Employee getEmployee(String pathEmployeeId);
    void deleteEmployee(String pathEmployeeId);
    void addSkillsToEmployee(Employee employee, List<Skill> skills);
}
