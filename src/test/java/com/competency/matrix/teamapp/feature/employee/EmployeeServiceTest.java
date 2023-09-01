package com.competency.matrix.teamapp.feature.employee;

import com.competency.matrix.teamapp.exceptions.request_data_exceptions.InvalidParameterException;
import com.competency.matrix.teamapp.exceptions.request_data_exceptions.InvalidRequestBodyException;
import com.competency.matrix.teamapp.exceptions.request_data_exceptions.PutIdMismatchException;
import com.competency.matrix.teamapp.exceptions.server_data_exceptions.ResourceNotFoundException;
import com.competency.matrix.teamapp.feature.employee.dto.EmployeeDto;
import com.competency.matrix.teamapp.feature.employee.dto.EmployeeMapper;
import com.competency.matrix.teamapp.feature.employeeSkill.dto.EmployeeSkillDto;
import com.competency.matrix.teamapp.feature.project.Project;
import com.competency.matrix.teamapp.feature.project.ProjectRepository;
import com.competency.matrix.teamapp.feature.skill.SkillRepository;
import com.competency.matrix.teamapp.feature.skill.dto.SkillDto;
import com.competency.matrix.teamapp.feature.skill.dto.SkillMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock private EmployeeRepository employeeRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private SkillRepository skillRepository;
    @Spy private EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);
    @Spy private SkillMapper skillMapper = Mappers.getMapper(SkillMapper.class);
    @InjectMocks
    private EmployeeService underTest;

    private Employee employee;
    private EmployeeDto employeeDto;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c");
        employee = new Employee(id, "TestEmployeeName", "TestEmployeeSurname", ZonedDateTime.now(), null,null, null);
        employeeDto = employeeMapper.entityToDto(employee);
    }

    @Test
    void when_getEmployees_should_callFindAll() {
        //GIVEN
        List<Employee> employeeList = List.of(employee);
        when(employeeRepository.findAll()).thenReturn(employeeList);

        //WHEN
        List<EmployeeDto> employeeDtoList = this.underTest.getEmployees();

        //THEN
        assertThat(employeeMapper.entityToDto(employeeList)).isEqualTo(employeeDtoList);
        verify(this.employeeRepository).findAll();
    }

    @Test
    void when_getEmployee_should_callFindById() {
        //GIVEN
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        //WHEN
        EmployeeDto employeeDto = this.underTest.getEmployee(id);

        //THEN
        assertThat(employeeMapper.entityToDto(employee)).isEqualTo(employeeDto);
        verify(this.employeeRepository).findById(id);
    }

    @Test
    void when_getEmployeeWithIdNotExistingInDatabase_should_throwInvalidParameterException() {
        //GIVEN
        when(employeeRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        //WHEN
        //THEN
        assertThatThrownBy(() -> underTest.getEmployee(id)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void when_getEmployeeWithNullId_should_throwInvalidParameterException() {
        //GIVEN
        id = null;

        //WHEN
        //THEN
        assertThatThrownBy(() -> underTest.getEmployee(id)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void when_getEmployeeByProjectIdWhichDoesntExist_should_throwInvalidParameterException() {
        //GIVEN
        when(projectRepository.existsById(id)).thenReturn(false);

        //WHEN
        //THEN
        assertThatThrownBy(()->underTest.getEmployeesByProjectId(id)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void when_getEmployeeByProjectId_should_callFindAllByProjectsId() {
        //GIVEN
        Project project = new Project(id, "TestProject", ZonedDateTime.now(), ZonedDateTime.now());
        employee.setProjects(List.of(project));
        List<Employee> employeeList = List.of(employee);
        when(projectRepository.existsById(id)).thenReturn(true);
        when(employeeRepository.findAllByProjectsId(id)).thenReturn(employeeList);

        //WHEN
        List<EmployeeDto> returnedEmployeeDtos = this.underTest.getEmployeesByProjectId(project.getId());

        //THEN
        verify(this.employeeRepository).findAllByProjectsId(id);
        assertThat(returnedEmployeeDtos).isEqualTo(employeeMapper.entityToDto(employeeList));
    }

    @Test
    void when_getEmployeesBySkillsNamesWhichAreEmpty_should_throwInvalidParameterException() {
        //GIVEN
        List<String> skillNames = new ArrayList<>();

        //WHEN
        //THEN
        assertThatThrownBy(()->underTest.getEmployeesBySkillsNames(skillNames)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void when_getEmployeesBySkillsNamesNotExistingInDatabase_should_throwInvalidParameterException() {
        //GIVEN
        List<String> skillNames = List.of("testName1", "testName2");
        when(skillRepository.existsByNameIn(skillNames)).thenReturn(false);

        //WHEN
        //THEN
        assertThatThrownBy(()->underTest.getEmployeesBySkillsNames(skillNames)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void when_getEmployeesBySkillsNames_should_callFindAllBySkillsSkillNameIn() {
        //GIVEN
        List<String> skillNames = List.of("testName1", "testName2");
        List<Employee> employeeList = List.of(employee);

        when(skillRepository.existsByNameIn(skillNames)).thenReturn(true);
        when(employeeRepository.findAllBySkillsSkillNameIn(skillNames)).thenReturn(employeeList);


        //WHEN
        List<EmployeeDto> returnedEmployeeDtos = this.underTest.getEmployeesBySkillsNames(skillNames);

        //THEN
        verify(this.employeeRepository).findAllBySkillsSkillNameIn(skillNames);
        assertThat(returnedEmployeeDtos).isEqualTo(employeeMapper.entityToDto(employeeList));
    }

    @Test
    void when_addEmployee_should_callSaveWithEmployeeArgument() {
        //GIVEN
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);

        //WHEN
        underTest.addEmployee(employeeDto);

        //THEN
        verify(employeeRepository)
                .save(employeeArgumentCaptor.capture());

        Employee capturedEmployee = employeeArgumentCaptor.getValue();
        assertThat(employeeMapper.entityToDto(capturedEmployee)).isEqualTo(employeeDto);
    }

    @Test
    void when_addEmployeeWithNullArgument_should_throwInvalidRequestBodyException() {
        //GIVEN
        employeeDto = null;

        //WHEN
        //THEN
        assertThatThrownBy(() ->
                underTest.addEmployee(employeeDto)).isInstanceOf(InvalidRequestBodyException.class);
    }

    @Test
    void when_addEmployees_should_callSaveWithEmployeeArgument() {
        //GIVEN
        ArgumentCaptor<List<Employee>> employeeArgumentCaptor = ArgumentCaptor.forClass(List.class);
        List<EmployeeDto> employeeDtoList = List.of(employeeDto);

        //WHEN
        underTest.addEmployees(employeeDtoList);

        //THEN
        verify(employeeRepository)
                .saveAll(employeeArgumentCaptor.capture());

        List<Employee> capturedEmployees = employeeArgumentCaptor.getValue();
        assertThat(employeeMapper.entityToDto(capturedEmployees)).isEqualTo(employeeDtoList);
    }

    @Test
    void when_addEmployeesWithNullArgument_should_throwInvalidRequestBodyException() {
        //GIVEN
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        employeeDtoList.add(employeeDto);
        employeeDtoList.add(null);

        //WHEN
        //THEN
        assertThatThrownBy(() ->
                underTest.addEmployees(employeeDtoList)).isInstanceOf(InvalidRequestBodyException.class);
    }

    @Test
    void when_updateEmployee_should_callSave() {
        //GIVEN
        when(employeeRepository.existsById(id)).thenReturn(true);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);

        //WHEN
        EmployeeDto returnedEmployeeDto = underTest.updateEmployee(id, employeeDto);

        //THEN
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        assertThat(employeeMapper.entityToDto(employeeArgumentCaptor.getValue())).isEqualTo(
                employeeMapper.entityToDto(employee));
        assertThat(returnedEmployeeDto).isEqualTo(employeeDto);
    }

    @Test
    void when_updateEmployeeWithDifferentIdInBody_should_throwPutIdMismatchException() {
        //GIVEN
        UUID differentId = UUID.fromString("6fc03087-d265-11e7-b8c6-83e29cd24f4c");

        //WHEN
        //THEN
        assertThatThrownBy(()->underTest.updateEmployee(differentId, employeeDto)).isInstanceOf(PutIdMismatchException.class);
    }

    @Test
    void when_updateEmployeeWithNullId_should_throwInvalidParameterException() {
        //GIVEN
        id = null;
        //WHEN
        //THEN
        assertThatThrownBy(()->underTest.updateEmployee(id, employeeDto)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void when_updateEmployeeWhichDoesntExist_should_throwResourceNotFoundException() {
        //GIVEN
        when(employeeRepository.existsById(id)).thenReturn(false);

        //WHEN
        //THEN
        assertThatThrownBy(()->underTest.updateEmployee(id, employeeDto)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void when_updateEmployeeDoesntExistAfterUpdate_should_throwResourceNotFoundException() {
        //GIVEN
        when(employeeRepository.existsById(id)).thenReturn(true);
        when(employeeRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        //WHEN
        //THEN
        assertThatThrownBy(()->underTest.updateEmployee(id, employeeDto)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void when_deleteEmployee_should_callDeleteById() {
        //GIVEN
        when(employeeRepository.existsById(id)).thenReturn(true);

        //WHEN
        underTest.deleteEmployee(id);

        //THEN
        verify(employeeRepository).deleteById(id);
    }
        @Test
    void when_deleteEmployeeWithNullId_should_throwInvalidParameterException() {
        //GIVEN
        id = null;
        //WHEN
        //THEN
        assertThatThrownBy(()->underTest.deleteEmployee(id)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void when_deleteEmployeeWhichDoesntExist_should_throwResourceNotFoundException() {
        //GIVEN
        when(employeeRepository.existsById(id)).thenReturn(false);

        //WHEN
        //THEN
        assertThatThrownBy(()->underTest.deleteEmployee(id)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void when_addSkillsToEmployee_should_callSave() {
        //GIVEN
        List<SkillDto> skillDtos = List.of(
                new SkillDto(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"), "testSkill"),
                new SkillDto(UUID.fromString("6fc03087-d265-11e7-b8c6-83e29cd24f4c"), "testSkill"));
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);

        assertNull(employeeDto.skills());

        //WHEN
        underTest.addSkillsToEmployee(employeeDto, skillDtos);

        //THEN
        verify(employeeRepository)
                .save(employeeArgumentCaptor.capture());

        EmployeeDto capturedEmployeeDto = employeeMapper.entityToDto(employeeArgumentCaptor.getValue());

        assertThat(capturedEmployeeDto.skills().stream().map(EmployeeSkillDto::skill).collect(Collectors.toList())).hasSameElementsAs(skillDtos);
    }
}