package com.competency.matrix.teamapp.feature.employee.dto;

import com.competency.matrix.teamapp.feature.employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "managerId", source = "manager.id")
    EmployeeDto entityToDto(Employee employee);
    List<EmployeeDto> entityToDto(Iterable<Employee> employees);
    Employee dtoToEntity(EmployeeDto employeeDto);
    List<Employee> dtoToEntity(Iterable<EmployeeDto> employeeDtos);
}
