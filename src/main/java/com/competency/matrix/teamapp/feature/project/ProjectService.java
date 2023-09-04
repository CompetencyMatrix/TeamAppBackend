package com.competency.matrix.teamapp.feature.project;

import com.competency.matrix.teamapp.feature.project.dto.ProjectDto;
import com.competency.matrix.teamapp.feature.project.dto.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService implements ProjectServiceInterface {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectDto> getProjects() {
        return projectMapper.entityToDto(projectRepository.findAll());
    }

    public void addProjects(List<ProjectDto> projects) {
        projectRepository.saveAll(projectMapper.dtoToEntity(projects));
    }

    public void addProject(ProjectDto project) {
        projectRepository.save(projectMapper.dtoToEntity(project));
    }
}
