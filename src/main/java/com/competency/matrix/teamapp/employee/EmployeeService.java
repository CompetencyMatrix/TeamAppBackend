package com.competency.matrix.teamapp.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> getEmployees(String skillId, String projectId) {

        if (skillId != null) {
        }
        if (projectId != null) {
            return employeeRepository.findAllByProjectsId(projectId);
        }
        return employeeRepository.findAll();
    }

    public void addEmployees(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(String pathEmployeeId, Employee employee) {
        if (!pathEmployeeId.equals(employee.getId())) {
            //TODO: exception
            throw new RuntimeException();
        } else {
            if (!employeeRepository.existsById(pathEmployeeId)) {

            } else {
            }
        }
        employeeRepository.save(employee);
    }
}
