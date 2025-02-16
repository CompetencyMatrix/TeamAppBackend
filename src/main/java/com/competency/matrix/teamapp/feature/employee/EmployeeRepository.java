package com.competency.matrix.teamapp.feature.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    List<Employee> findAllByProjectsId(UUID projectId);

    List<Employee> findAllBySkillsSkillNameIn(List<String> skillsNames);

    List<Employee> findAllByName(String employeeName);

}
