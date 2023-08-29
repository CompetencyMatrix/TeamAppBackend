package com.competency.matrix.teamapp.startup;

import com.competency.matrix.teamapp.feature.employee.EmployeeService;
import com.competency.matrix.teamapp.feature.employee.dto.EmployeeDto;
import com.competency.matrix.teamapp.feature.project.ProjectService;
import com.competency.matrix.teamapp.feature.project.dto.ProjectDto;
import com.competency.matrix.teamapp.feature.skill.SkillService;
import com.competency.matrix.teamapp.feature.skill.dto.SkillDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Value("#{'${tapp.skills.initial}'.split(',')}")
    final List<String> skillNames;
    @Value("#{'${tapp.employees.initial}'.split(',')}")
    final List<String> employeeNames;
    @Value("#{'${tapp.projects.initial}'.split(',')}")
    final List<String> projectNames;
    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final SkillService skillService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        List<SkillDto> skills = initializeSkills();
        List<ProjectDto> projects = initializeProjects();
        initializeEmployees(skills, projects);
    }

    @Transactional
    public List<SkillDto> initializeSkills() {
        List<SkillDto> skills = skillNames.stream()
                .map(name -> new SkillDto(null, name))
                .collect(Collectors.toList());
        skillService.addSkills(skills);
        return skillService.getSkills();
    }


    @Transactional
    public List<ProjectDto> initializeProjects() {
        List<ProjectDto> projects = projectNames.stream()
                .map(name -> new ProjectDto(null, name, ZonedDateTime.now().minusDays(RandomGenerator.getDefault().nextInt(3650)), ZonedDateTime.now().plusDays(RandomGenerator.getDefault().nextInt(3650))))
                .collect(Collectors.toList());
        projectService.addProjects(projects);
        return projectService.getProjects();
    }

    @Transactional
    public List<EmployeeDto> initializeEmployees(List<SkillDto> skills, List<ProjectDto> projects) {
        List<EmployeeDto> employees = employeeNames.stream()
                .map(name -> new EmployeeDto(null, name, name + "owski", ZonedDateTime.now().withHour(0).minusDays(RandomGenerator.getDefault().nextInt(3650)), null, null, projects))
                .collect(Collectors.toList());
        employeeService.addEmployees(employees);
        employees = employeeService.getEmployees();
        employees.forEach(employee -> employeeService.addSkillsToEmployee(employee, skills.subList(RandomGenerator.getDefault().nextInt(skills.size() - 1), skills.size() - 1)));
        return employees;
    }
}
