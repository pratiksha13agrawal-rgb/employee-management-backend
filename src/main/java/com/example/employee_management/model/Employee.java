package com.example.employee_management.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String department;
    private double salary;
    private String role;
    private String joinDate;

}
