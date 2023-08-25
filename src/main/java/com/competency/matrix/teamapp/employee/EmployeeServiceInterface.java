package com.competency.matrix.teamapp.employee;

import com.competency.matrix.teamapp.skill.Skill;

import java.util.List;

public interface EmployeeServiceInterface {
    public List<Employee> getEmployees(List<String> requiredSkillsNames, String employeesCommonProjectId);
    public void addEmployees(List<Employee> employees);
    public void addEmployee(Employee employee);
    public void updateEmployee(String pathEmployeeId, Employee employee);
    public void addSkillsToEmployee(Employee employee, List<Skill> skills);
}
