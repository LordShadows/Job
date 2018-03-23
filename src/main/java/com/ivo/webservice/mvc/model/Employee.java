package com.ivo.webservice.mvc.model;

import java.sql.Date;

public class Employee {

    private int id;
    private String fio;
    private Date dateOfBirth;
    private int salary;
    private String department;

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}
