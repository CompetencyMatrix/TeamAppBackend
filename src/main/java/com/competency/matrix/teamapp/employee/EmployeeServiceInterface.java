package com.competency.matrix.teamapp.employee;

import com.competency.matrix.teamapp.employeeSkill.EmployeeSkill;
import com.competency.matrix.teamapp.employeeSkill.EmployeeSkillId;
import com.competency.matrix.teamapp.employeeSkill.EmployeeSkillLevel;
import com.competency.matrix.teamapp.exceptions.InvalidParameterException;
import com.competency.matrix.teamapp.exceptions.NoMatchForParametersFound;
import com.competency.matrix.teamapp.skill.Skill;

import java.util.List;
import java.util.stream.Collectors;

public interface EmployeeServiceInterface {
    public List<Employee> getEmployees(List<String> requiredSkillsNames, String employeesCommonProjectId);
    public void addEmployees(List<Employee> employees);
    public void addEmployee(Employee employee);
    public void updateEmployee(String pathEmployeeId, Employee employee);
    public void addSkillsToEmployee(Employee employee, List<Skill> skills);
}
