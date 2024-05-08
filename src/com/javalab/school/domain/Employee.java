package com.javalab.school.domain;

import java.time.LocalDateTime;

public class Employee {
    private String employeeId;
    private String name;
    private String departmentId;
    private double salary;
    private LocalDateTime hireDate;
    private String address;
    private String contact;

    public Employee(String employeeId, String name, String departmentId, double salary, LocalDateTime hireDate, String address, String contact) {
        this.employeeId = employeeId;
        this.name = name;
        this.departmentId = departmentId;
        this.salary = salary;
        this.hireDate = hireDate;
        this.address = address;
        this.contact = contact;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDateTime getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
