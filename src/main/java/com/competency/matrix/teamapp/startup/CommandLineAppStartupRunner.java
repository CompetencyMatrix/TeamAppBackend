package com.competency.matrix.teamapp.startup;

import com.competency.matrix.teamapp.feature.employee.Employee;
import com.competency.matrix.teamapp.feature.employee.EmployeeService;
import com.competency.matrix.teamapp.feature.project.Project;
import com.competency.matrix.teamapp.feature.project.ProjectService;
import com.competency.matrix.teamapp.feature.skill.Skill;
import com.competency.matrix.teamapp.feature.skill.SkillService;
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
        List<Skill> skills = initializeSkills();
        List<Project> projects = initializeProjects();
        initializeEmployees(skills, projects);
    }

    @Transactional
    public List<Skill> initializeSkills() {
        List<Skill> skills = skillNames.stream()
                .map(name -> new Skill(null, name))
                .collect(Collectors.toList());
        skillService.addSkills(skills);
        return skills;
    }


    @Transactional
    public List<Project> initializeProjects() {
        List<Project> projects = projectNames.stream()
                .map(name -> new Project(null, name, ZonedDateTime.now().minusDays(RandomGenerator.getDefault().nextInt(3650)), ZonedDateTime.now().plusDays(RandomGenerator.getDefault().nextInt(3650))))
                .collect(Collectors.toList());
        projectService.addProjects(projects);
        return projects;
    }

    @Transactional
    public List<Employee> initializeEmployees(List<Skill> skills, List<Project> projects) {
        List<Employee> employees = employeeNames.stream()
                .map(name -> new Employee(null, name, name + "owski", ZonedDateTime.now().withHour(0).minusDays(RandomGenerator.getDefault().nextInt(3650)), null, null, projects))
                .collect(Collectors.toList());
        employees.forEach(employee -> employeeService.addSkillsToEmployee(employee, skills.subList(RandomGenerator.getDefault().nextInt(skills.size() - 1), skills.size() - 1)));
        return employees;
    }
}
