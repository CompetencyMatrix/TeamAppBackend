package com.competency.matrix.teamapp.feature.project;

import com.competency.matrix.teamapp.feature.project.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectServiceInterface projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjects() {
        return ResponseEntity.ok(projectService.getProjects());
    }
}
