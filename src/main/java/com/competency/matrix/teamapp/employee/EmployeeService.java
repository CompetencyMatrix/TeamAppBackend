package com.competency.matrix.teamapp.employee;

import com.competency.matrix.teamapp.employeeSkill.EmployeeSkill;
import com.competency.matrix.teamapp.employeeSkill.EmployeeSkillId;
import com.competency.matrix.teamapp.employeeSkill.EmployeeSkillLevel;
import com.competency.matrix.teamapp.exceptions.request_data_exceptions.InvalidRequestBodyException;
import com.competency.matrix.teamapp.exceptions.server_data_exceptions.ConflictWithServerDataException;
import com.competency.matrix.teamapp.exceptions.server_data_exceptions.DatabaseDeleteFailException;
import com.competency.matrix.teamapp.exceptions.server_data_exceptions.ResourceNotFoundException;
import com.competency.matrix.teamapp.exceptions.request_data_exceptions.InvalidParameterException;
import com.competency.matrix.teamapp.exceptions.request_data_exceptions.PutIdMismatchException;
import com.competency.matrix.teamapp.project.ProjectRepository;
import com.competency.matrix.teamapp.skill.Skill;
import com.competency.matrix.teamapp.skill.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements EmployeeServiceInterface {
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;

    @Override
    public List<Employee> getEmployees(List<String> requiredSkillsNames, String employeesCommonProjectId) {
        if (requiredSkillsNames != null) {
            return getEmployeesBySkillsNames(requiredSkillsNames);
        }
        if (employeesCommonProjectId != null) {
            return getEmployeesByProjectId(employeesCommonProjectId);
        }
        return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public void addEmployees(List<Employee> employees) {
        if (employees.stream().anyMatch( employee -> employee != null && employeeRepository.existsById(employee.getId()))) {
            throw new InvalidParameterException("Tried to add employee with ID already present in the database.");
        }
        saveAllToDatabase(employees);
    }

    @Override
    @Transactional
    public void addEmployee(Employee employee) {
        if (employee != null && employeeRepository.existsById(employee.getId())) {
            throw new InvalidParameterException("Tried to add employee with ID already present in the database.");
        }
        saveToDatabase(employee);
    }

    @Override
    @Transactional
    public Employee updateEmployee(String pathEmployeeId, Employee employee) {
        if (!pathEmployeeId.equals(employee.getId())) {
            throw new PutIdMismatchException("Tried to update update employee with different ID than in the path.");
        } else {
            if (!employeeRepository.existsById(pathEmployeeId)) {
                throw new ResourceNotFoundException("Employee with specified ID doesn't exist in the database.");
            } else {
                saveToDatabase(employee);
            }
        }
        return employeeRepository.findById(pathEmployeeId).orElseThrow(() -> new ResourceNotFoundException("Employee was deleted during or after update."));
    }

    @Override
    public Employee getEmployee(String pathEmployeeId) {
        return employeeRepository.findById(pathEmployeeId).orElseThrow(() -> new ResourceNotFoundException("No Employee with provided ID found."));
    }

    @Override
    @Transactional
    public void deleteEmployee(String pathEmployeeId) {
        if (!employeeRepository.existsById(pathEmployeeId)) {
            throw new ResourceNotFoundException("Employee's ID not found in the database.");
        }
        try {
            employeeRepository.deleteById(pathEmployeeId);
        } catch (IllegalArgumentException exception) {
            throw new DatabaseDeleteFailException("Delete failed: provided ID is 'null'.");
        }
    }

    @Override
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
        } catch (IllegalArgumentException exception) {
            throw new InvalidRequestBodyException("Tried to save Employee that is 'null'.");
        } catch (OptimisticLockingFailureException exception) {
            throw new ConflictWithServerDataException("Conflict during saving in database - version of the data differs from expected. " + exception.getMessage());
        }
    }

    private void saveAllToDatabase(List<Employee> employees) {
        try {
            employeeRepository.saveAll(employees);
        } catch (IllegalArgumentException exception) {
            throw new InvalidRequestBodyException("Tried to save Employee that is 'null'.");
        } catch (OptimisticLockingFailureException exception) {
            throw new ConflictWithServerDataException("Conflict during saving in database - version of the data differs from expected. " + exception.getMessage());
        }
    }

    private List<Employee> getEmployeesByProjectId(String employeesCommonProjectId) {
        if (!projectRepository.existsById(employeesCommonProjectId)) {
            throw new InvalidParameterException("Project with ID: " + employeesCommonProjectId + " doesn't exist.");
        }
        return employeeRepository.findAllByProjectsId(employeesCommonProjectId);
    }

    private List<Employee> getEmployeesBySkillsNames(List<String> requiredSkillsNames) {
        if (requiredSkillsNames.isEmpty()) {
            throw new InvalidParameterException("Empty list of required Employee's skills passed.");
        }
        if (!skillRepository.existsByNameIn(requiredSkillsNames)) {
            throw new InvalidParameterException("Skills with specified names don't exist.");
        }
        return employeeRepository.findAllBySkillsSkillNameIn(requiredSkillsNames);
        }
}
