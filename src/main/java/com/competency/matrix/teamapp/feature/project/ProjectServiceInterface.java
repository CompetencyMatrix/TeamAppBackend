package com.competency.matrix.teamapp.feature.project;

import com.competency.matrix.teamapp.feature.project.dto.ProjectDto;

import java.util.List;

public interface ProjectServiceInterface {
    List<ProjectDto> getProjects();

    void addProjects(List<ProjectDto> projects);

    void addProject(ProjectDto project);
}
