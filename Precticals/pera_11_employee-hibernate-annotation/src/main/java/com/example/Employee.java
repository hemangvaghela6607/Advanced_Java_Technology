package com.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity                          // this class is stored in the database
@Table(name = "employee")        // ... in the table "employee"
public class Employee {

    @Id                                                   // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // MySQL AUTO_INCREMENT
    @Column(name = "emp_id")
    private int id;

    @Column(name = "emp_name", length = 100, nullable = false)
    private String name;

    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "salary")
    private double salary;

    // Hibernate needs a no-argument constructor
    public Employee() {
    }

    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("%-4d %-15s %-12s %,12.2f", id, name, department, salary);
    }
}
