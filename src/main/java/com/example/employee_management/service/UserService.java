package com.example.employee_management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employee_management.model.Employee;
import com.example.employee_management.model.User;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public String makeEmployee(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        //check already employee exits
        if(employeeRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User is already an employee";
        }

        Employee employee = new Employee();
        employee.setName(user.getName());
        employee.setEmail(user.getEmail());
        employee.setRole(user.getRole());
        employee.setDepartment("N/A");
        employee.setPhone("N/A");
        employee.setSalary(0.0);
        employee.setJoinDate(LocalDate.now().toString());
        employeeRepository.save(employee);
        
        return "User successfully converted to Employee!";
    }

}
