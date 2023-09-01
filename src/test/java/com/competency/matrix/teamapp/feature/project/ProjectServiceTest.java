package com.competency.matrix.teamapp.feature.project;

import com.competency.matrix.teamapp.feature.employee.Employee;
import com.competency.matrix.teamapp.feature.employee.dto.EmployeeDto;
import com.competency.matrix.teamapp.feature.project.dto.ProjectDto;
import com.competency.matrix.teamapp.feature.project.dto.ProjectMapper;
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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock private ProjectRepository projectRepository;
    @Spy private ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);
    @InjectMocks
    private ProjectService underTest;

    private Project project;
    private ProjectDto projectDto;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c");
        project = new Project(id, "TestProject", ZonedDateTime.now(), ZonedDateTime.now());
        projectDto = projectMapper.entityToDto(project);
    }

    @Test
    void when_getProjects_should_callFindAll() {
        //GIVEN
        List<Project> projectList = List.of(project);
        when(projectRepository.findAll()).thenReturn(projectList);

        //WHEN
        List<ProjectDto> projectDtoList = this.underTest.getProjects();

        //THEN
        assertThat(projectMapper.entityToDto(projectList)).isEqualTo(projectDtoList);
        verify(this.projectRepository).findAll();
    }

    @Test
    void when_addProjects_should_callSaveAll() {
        //GIVEN
        List<ProjectDto> projectDtoList = List.of(projectDto);
        ArgumentCaptor<List> projectArgumentCaptor = ArgumentCaptor.forClass(List.class);

        //WHEN
        this.underTest.addProjects(projectDtoList);

        //THEN
        verify(this.projectRepository)
                .saveAll(projectArgumentCaptor.capture());
        assertThat(projectMapper.entityToDto(projectArgumentCaptor.getValue()))
                .isEqualTo(projectDtoList);
    }

    @Test
    void when_addProject_should_callSave() {
        //GIVEN
        ArgumentCaptor<Project> projectArgumentCaptor = ArgumentCaptor.forClass(Project.class);

        //WHEN
        this.underTest.addProject(projectDto);

        //THEN
        verify(this.projectRepository)
                .save(projectArgumentCaptor.capture());
        assertThat(projectMapper.entityToDto(projectArgumentCaptor.getValue()))
                .isEqualTo(projectDto);
    }
}