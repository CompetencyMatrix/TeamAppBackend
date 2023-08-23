package com.competency.matrix.teamapp.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public void addEmployees(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}
