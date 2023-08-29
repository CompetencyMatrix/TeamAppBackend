package com.competency.matrix.teamapp.feature.project.dto;

import com.competency.matrix.teamapp.feature.project.Project;
import com.competency.matrix.teamapp.feature.project.dto.ProjectDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
        ProjectDto entityToDto(Project project);
        List<ProjectDto> entityToDto(Iterable<Project> projects);
        Project dtoToEntity(ProjectDto projectDto);
        List<Project> dtoToEntity(Iterable<ProjectDto> projectDtos);
}
