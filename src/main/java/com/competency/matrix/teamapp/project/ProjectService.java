package com.competency.matrix.teamapp.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public void addProjects(List<Project> projects) {
        projectRepository.saveAll(projects);
    }

    public void addProject(Project project) {
        projectRepository.save(project);
    }
}
