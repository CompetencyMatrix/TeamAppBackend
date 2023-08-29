package com.competency.matrix.teamapp.feature.employeeSkill.dto;

import com.competency.matrix.teamapp.feature.employeeSkill.EmployeeSkill;
import com.competency.matrix.teamapp.feature.employeeSkill.dto.EmployeeSkillDto;
import com.competency.matrix.teamapp.feature.project.Project;
import com.competency.matrix.teamapp.feature.project.dto.ProjectDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeSkillMapper {
    EmployeeSkillDto entityToDto(EmployeeSkill employeeSkill);
    List<EmployeeSkillDto> entityToDto(Iterable<EmployeeSkill> employeeSkills);
    EmployeeSkill dtoToEntity(EmployeeSkillDto employeeSkillDto);
    List<EmployeeSkill> dtoToEntity(Iterable<EmployeeSkillDto> employeeSkillDtos);
}
