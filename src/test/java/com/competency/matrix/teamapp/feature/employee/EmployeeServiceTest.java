package com.competency.matrix.teamapp.feature.employee;

import com.competency.matrix.teamapp.TeamappApplication;
import com.competency.matrix.teamapp.feature.employee.dto.EmployeeDto;
import com.competency.matrix.teamapp.feature.employee.dto.EmployeeMapper;
import com.competency.matrix.teamapp.feature.project.Project;
import com.competency.matrix.teamapp.feature.project.ProjectRepository;
import com.competency.matrix.teamapp.feature.project.dto.ProjectDto;
import com.competency.matrix.teamapp.feature.skill.SkillRepository;
import com.competency.matrix.teamapp.feature.skill.dto.SkillMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock private EmployeeRepository employeeRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private SkillRepository skillRepository;
    @Spy EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);
    @Spy SkillMapper skillMapper = Mappers.getMapper(SkillMapper.class);

    @InjectMocks
    private EmployeeService underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void getEmployees_should_call_findAll() {
        //GIVEN
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(UUID.randomUUID(), "TestEmployeeName", "TestEmployeeSurname", ZonedDateTime.now(), null,null, null));
        when(employeeRepository.findAll()).thenReturn(employeeList);

        //WHEN
        List<EmployeeDto> employeeDtoList = this.underTest.getEmployees();
        //TODO - czy mocked repository nie dodaje ID ?
        //        System.out.println(employeeDtoList);
        //        System.out.println();

        //THEN
        assertEquals(employeeMapper.entityToDto(employeeList),employeeDtoList);
        verify(this.employeeRepository).findAll();
    }

    @Test
    @Disabled
    void getEmployeesByProjectId() {
        //GIVEN
        UUID projectId = UUID.randomUUID();
        Project project = new Project(projectId, "TestProject", ZonedDateTime.now(), ZonedDateTime.now());
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(UUID.randomUUID(), "TestEmployeeName", "TestEmployeeSurname", ZonedDateTime.now(), null,null, List.of(project)));
        when(employeeRepository.findAllByProjectsId(any(UUID.class))).thenReturn(employeeList);

        //WHEN
        this.underTest.getEmployeesByProjectId(project.getId());

        //THEN
        verify(this.employeeRepository).findAllByProjectsId(project.getId());
    }

    @Test
    @Disabled
    void getEmployeesBySkillsNames() {
    }

    @Test
    void addEmployees() {
        //GIVEN
        EmployeeDto employeeDto = new EmployeeDto(UUID.randomUUID(), "TestEmployeeName", "TestEmployeeSurname", ZonedDateTime.now(), null,null, null);

        //WHEN
        underTest.addEmployee(employeeDto);

        //THEN
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);

        verify(employeeRepository)
                .save(employeeArgumentCaptor.capture());

        Employee capturedEmployee = employeeArgumentCaptor.getValue();
//TODO: czemu ten test nie przechodzi jak mapuje dtoToEntity i porownuje Entities, ale juz Dtos porownuje poprawnie? czy to kwestia jak recordy sie porownuje?
//        System.out.println(employeeDto);
//        System.out.println();
        assertThat(employeeMapper.entityToDto(capturedEmployee)).isEqualTo(employeeDto);
    }

    @Test
    @Disabled
    void addEmployee() {
    }

    @Test
    @Disabled
    void updateEmployee() {
    }

    @Test
    @Disabled
    void getEmployee() {
    }

    @Test
    @Disabled
    void deleteEmployee() {
    }

    @Test
    @Disabled
    void addSkillsToEmployee() {
    }
}