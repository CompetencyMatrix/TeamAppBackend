package com.competency.matrix.teamapp.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    List<Employee> findAllByProjectsId(String projectId);

    List<Employee> findAllBySkillsSkillNameIn(List<String> skillsNames);

}
