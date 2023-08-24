package com.competency.matrix.teamapp.project;

import java.util.List;

public interface ProjectServiceInterface {
    public List<Project> getProjects();
    public void addProjects(List<Project> projects);
    public void addProject(Project project);
}
