package com.competency.matrix.teamapp.employee;

import com.competency.matrix.teamapp.employeeSkill.EmployeeSkill;
import com.competency.matrix.teamapp.employeeSkill.EmployeeSkillId;
import com.competency.matrix.teamapp.employeeSkill.EmployeeSkillLevel;
import com.competency.matrix.teamapp.exceptions.*;
import com.competency.matrix.teamapp.project.ProjectRepository;
import com.competency.matrix.teamapp.skill.Skill;
import com.competency.matrix.teamapp.skill.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements EmployeeServiceInterface {
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
                throw new NoMatchForParametersFoundException("No Employees with skills from list: " + requiredSkillsNames + " found.");
            }
        }

        if (employeesCommonProjectId != null) {
            if (!projectRepository.existsById(employeesCommonProjectId)) {
                throw new InvalidParameterException("Project with ID: " + employeesCommonProjectId + " doesn't exist.");
            }
            List<Employee> foundEmployees = employeeRepository.findAllByProjectsId(employeesCommonProjectId);
            if (foundEmployees.isEmpty()) {
                throw new NoMatchForParametersFoundException("No Employees assigned to the project with ID: " + employeesCommonProjectId + " found.");
            }
        }

        return employeeRepository.findAll();
    }

    public void addEmployees(List<Employee> employees) {
        try {
            employeeRepository.saveAll(employees);
        }
        catch (IllegalArgumentException exception) {
            throw new DatabaseSaveFailException("Tried to save Employee that is 'null'.");
        }
        catch (OptimisticLockingFailureException exception) {
            throw new DatabaseSaveFailException("Employee doesn't meet database constraints. " + exception.getMessage());
        }
    }

    public void addEmployee(Employee employee) {
        saveToDatabase(employee);
    }

    public void updateEmployee(String pathEmployeeId, Employee employee) {
        if (!pathEmployeeId.equals(employee.getId())) {
            throw new PutIdMismatchException("Tried to update update employee with different ID than in the path.");
        } else {
            if (!employeeRepository.existsById(pathEmployeeId)) {
                throw new ResourceNotFoundException("Employee with specified ID doesn't exist in the database.");
            } else {
                saveToDatabase(employee);
            }
        }
    }

    public void addSkillsToEmployee(Employee employee, List<Skill> skills) {
        Set<EmployeeSkill> employeeSkills = skills.stream().map(skill -> new EmployeeSkill(
                new EmployeeSkillId(employee.getId(), skill.getId()),
                employee,
                skill,
                EmployeeSkillLevel.JUNIOR)).collect(Collectors.toSet());
        employee.setSkills(employeeSkills);
        employeeRepository.save(employee);
    }

    private void saveToDatabase(Employee employee) {
        try {
            employeeRepository.save(employee);
        }
        catch (IllegalArgumentException exception) {
            throw new DatabaseSaveFailException("Tried to save Employee that is 'null'.");
        }
        catch (OptimisticLockingFailureException exception) {
            throw new DatabaseSaveFailException("Employee doesn't meet database constraints. " + exception.getMessage());
        }
    }
}
