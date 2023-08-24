package com.competency.matrix.teamapp.employee;

import com.competency.matrix.teamapp.exceptions.InvalidParameterException;
import com.competency.matrix.teamapp.exceptions.NoMatchForParametersFound;
import com.competency.matrix.teamapp.project.ProjectRepository;
import com.competency.matrix.teamapp.skill.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;

    public List<Employee> getEmployees(List<String> requiredSkillsNames, String employeesCommonProjectId) {

        if (requiredSkillsNames != null) {
            if (requiredSkillsNames.isEmpty()) {
                throw new InvalidParameterException("Empty list of required Employee's skills passed.");
            }

            if (!skillRepository.existsByNameIn(requiredSkillsNames)) {
                throw new InvalidParameterException("Skills with specified names don't exist.");
            }

            List<Employee> foundEmployees = employeeRepository.findAllBySkillsSkillNameIn(requiredSkillsNames);
            if (foundEmployees.isEmpty()) {
                throw new NoMatchForParametersFound("No Employees with skills from list: " + requiredSkillsNames + " found.");
            }
        }

        if (employeesCommonProjectId != null) {
            if (!projectRepository.existsById(employeesCommonProjectId)) {
                throw new InvalidParameterException("Project with ID: " + employeesCommonProjectId + " doesn't exist.");
            }
            List<Employee> foundEmployees = employeeRepository.findAllByProjectsId(employeesCommonProjectId);
            if (foundEmployees.isEmpty()) {
                throw new NoMatchForParametersFound("No Employees assigned to the project with ID: " + employeesCommonProjectId + " found.");
            }
        }

        return employeeRepository.findAll();
    }

    public void addEmployees(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(String pathEmployeeId, Employee employee) {
        if (!pathEmployeeId.equals(employee.getId())) {
            //TODO: exception
            throw new RuntimeException();
        } else {
            if (!employeeRepository.existsById(pathEmployeeId)) {

            } else {
            }
        }
        employeeRepository.save(employee);
    }
}
