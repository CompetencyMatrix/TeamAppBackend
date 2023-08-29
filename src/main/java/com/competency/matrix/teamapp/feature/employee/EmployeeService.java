package com.competency.matrix.teamapp.feature.employee;

import com.competency.matrix.teamapp.exceptions.request_data_exceptions.InvalidParameterException;
import com.competency.matrix.teamapp.exceptions.request_data_exceptions.InvalidRequestBodyException;
import com.competency.matrix.teamapp.exceptions.request_data_exceptions.PutIdMismatchException;
import com.competency.matrix.teamapp.exceptions.server_data_exceptions.ConflictWithServerDataException;
import com.competency.matrix.teamapp.exceptions.server_data_exceptions.ResourceNotFoundException;
import com.competency.matrix.teamapp.feature.employee.dto.EmployeeDto;
import com.competency.matrix.teamapp.feature.employee.dto.EmployeeMapper;
import com.competency.matrix.teamapp.feature.employeeSkill.EmployeeSkill;
import com.competency.matrix.teamapp.feature.employeeSkill.EmployeeSkillLevel;
import com.competency.matrix.teamapp.feature.project.ProjectRepository;
import com.competency.matrix.teamapp.feature.skill.Skill;
import com.competency.matrix.teamapp.feature.skill.SkillRepository;
import com.competency.matrix.teamapp.feature.skill.dto.SkillDto;
import com.competency.matrix.teamapp.feature.skill.dto.SkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements EmployeeServiceInterface {
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;
    private final EmployeeMapper employeeMapper;
    private final SkillMapper skillMapper;

    @Override
    public List<EmployeeDto> getEmployees() {
        return employeeMapper.entityToDto(employeeRepository.findAll());
    }

    @Override
    public List<EmployeeDto> getEmployeesByProjectId(UUID employeesCommonProjectId) {
        if (!projectRepository.existsById(employeesCommonProjectId)) {
            throw new InvalidParameterException("Project with ID: " + employeesCommonProjectId + " doesn't exist.");
        }
        return employeeMapper.entityToDto(employeeRepository.findAllByProjectsId(employeesCommonProjectId));
    }

    @Override
    public List<EmployeeDto> getEmployeesBySkillsNames(List<String> requiredSkillsNames) {
        if (requiredSkillsNames.isEmpty()) {
            throw new InvalidParameterException("Empty list of required Employee's skills passed.");
        }
        if (!skillRepository.existsByNameIn(requiredSkillsNames)) {
            throw new InvalidParameterException("Skills with specified names don't exist.");
        }
        return employeeMapper.entityToDto(employeeRepository.findAllBySkillsSkillNameIn(requiredSkillsNames));
    }

    @Override
    @Transactional
    public void addEmployees(List<EmployeeDto> employeeDtos) {
        if (employeeDtos.stream().anyMatch(Objects::isNull)) {
            throw new InvalidRequestBodyException("Tried to add employee that was null.");
        }
        List<Employee> employees = employeeDtos.stream().map(this::convertFromDtoToEntity).collect(Collectors.toList());

        saveAllToDatabase(employees);
    }

    @Override
    @Transactional
    public void addEmployee(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            throw new InvalidRequestBodyException("Tried to add employee that was null.");
        }
        saveToDatabase(convertFromDtoToEntity(employeeDto));
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(UUID employeeId, EmployeeDto employeeDto) {
        if (employeeId == null) {
            throw new InvalidParameterException("Provided Employee ID was null.");
        }
        if (!employeeId.equals(employeeDto.id())) {
            throw new PutIdMismatchException("Tried to update update employee with different ID than in the path.");
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee with specified ID doesn't exist in the database.");
        }

        Employee employee = employeeMapper.dtoToEntity(employeeDto);
        saveToDatabase(employee);

        return employeeMapper.entityToDto(employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee was deleted during or after update."))
        );
    }

    @Override
    public EmployeeDto getEmployee(UUID employeeId) {
        if (employeeId == null) {
            throw new InvalidParameterException("Provided Employee ID was null.");
        }
        return employeeMapper.entityToDto(employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee with provided ID found."))
        );
    }

    @Override
    @Transactional
    public void deleteEmployee(UUID employeeId) {
        if (employeeId == null) {
            throw new InvalidParameterException("Provided Employee ID was null.");
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee's ID not found in the database.");
        }
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public void addSkillsToEmployee(EmployeeDto employeeDto, List<SkillDto> skillDtos) {
        EmployeeSkillLevel initialLevel = EmployeeSkillLevel.JUNIOR;
        Employee employee = employeeMapper.dtoToEntity(employeeDto);
        List<Skill> skills = skillMapper.dtoToEntity(skillDtos);

        Set<EmployeeSkill> employeeSkills = skills.stream().map(skill -> new EmployeeSkill(
                employee,
                skill,
                initialLevel)).collect(Collectors.toSet());
        if (employee.getSkills() != null) {
            employeeSkills.addAll(employee.getSkills());
        }
        employee.setSkills(employeeSkills);
        saveToDatabase(employee);
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

    private Employee convertFromDtoToEntity(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.dtoToEntity(employeeDto);
        convertSkillsFromDtoInEntity(employee);
        return employee;
    }

    private void convertSkillsFromDtoInEntity(Employee employee) {
        Set<EmployeeSkill> skills = employee.getSkills();
        if (skills != null) {
            skills.forEach(skill -> skill.setEmployee(employee));
            employee.setSkills(skills);
        }
    }

}
