package com.competency.matrix.teamapp.project;

import java.util.List;

public interface ProjectServiceInterface {
    List<Project> getProjects();
    void addProjects(List<Project> projects);
    void addProject(Project project);
}
