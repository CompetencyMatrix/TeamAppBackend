package com.competency.matrix.teamapp.startup;

import com.competency.matrix.teamapp.employee.Employee;
import com.competency.matrix.teamapp.employee.EmployeeService;
import com.competency.matrix.teamapp.employeeSkill.EmployeeSkill;
import com.competency.matrix.teamapp.employeeSkill.EmployeeSkillLevel;
import com.competency.matrix.teamapp.project.Project;
import com.competency.matrix.teamapp.project.ProjectService;
import com.competency.matrix.teamapp.skill.Skill;
import com.competency.matrix.teamapp.skill.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final SkillService skillService;

    @Value("#{'${tapp.skills.initial}'.split(',')}") final List<String> skillNames;
    @Value("#{'${tapp.employees.initial}'.split(',')}") final List<String> employeeNames;
    @Value("#{'${tapp.projects.initial}'.split(',')}") final List<String> projectNames;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        List<Skill> skills = initializeSkills();
        List<Project> projects = initializeProjects();
        initializeEmployees(skills,projects);
    }

    @Transactional
    public List<Skill> initializeSkills() {
        List<Skill> skills = skillNames.stream()
                .map(name -> new Skill(null, name, null))
                .collect(Collectors.toList());
        skillService.addSkills(skills);
        return skills;
        }


    @Transactional
    public List<Project> initializeProjects() {
        List<Project> projects = projectNames.stream()
                .map(name -> new Project(null, name, ZonedDateTime.now(), ZonedDateTime.now()))
                .collect(Collectors.toList());
        projectService.addProjects(projects);
        return projects;
    }

    @Transactional
    public List<Employee> initializeEmployees( List<Skill> skills, List<Project> projects ){
        List<Employee> employees = employeeNames.stream()
                .map(name -> {
                    Employee employee = new Employee(null, name, name+"owski", ZonedDateTime.now(), null, null, projects);
                    //TODO: save this EmployeeSkill in the database separately first
                    employee.setSkills(skills.stream().map(skill -> new EmployeeSkill(null, employee, skill, EmployeeSkillLevel.INTERMEDIATE)).collect(Collectors.toSet()));
                    return employee;
                })
                .collect(Collectors.toList());
        employeeService.addEmployees(employees);
        return employees;
    }
}
