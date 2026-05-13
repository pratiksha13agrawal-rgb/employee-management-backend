package com.example.employee_management.service;

import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Long id, Employee employee) {
        employee.setId(id);
        Employee saved = employeeRepository.save(employee);

        //update role in user table
        userRepository.findByEmail(employee.getEmail()).ifPresent(user -> {
            user.setRole(employee.getRole());
            userRepository.save(user);
        });
        return saved;
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee getByEmail(String email) {
        return employeeRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Map<String, Object> bulkSave(List<Employee> employees) {
       List<Employee> saved = new ArrayList<>();
       List<String> skipped = new ArrayList<>();
       
       List<Employee> unique = employees.stream().collect(Collectors.toMap(Employee::getEmail, e-> e, (e1,e2) -> e1))
        .values()
        .stream()
        .collect(Collectors.toList());

       for(Employee emp: unique) {
        emp.setId(null);
        if(employeeRepository.findByEmail(emp.getEmail()).isPresent()) {
            skipped.add(emp.getEmail());
        } else {
            saved.add(emp);
        }
       }

       employeeRepository.saveAll(saved);
       return Map.of(
        "saved",saved.size(),
        "skipped", skipped.size(),
        "skippedEmails", skipped
       );
    }

}
