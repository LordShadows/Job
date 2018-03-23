package com.ivo.webservice.mvc.model;

public class AvgSalary {

    private String department;
    private int avgSalary;

    public AvgSalary() {
    }

    public AvgSalary(String department, int avgSalary) {
        this.department = department;
        this.avgSalary = avgSalary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getAvgSalary() {
        return avgSalary;
    }

    public void setAvgSalary(int avgSalary) {
        this.avgSalary = avgSalary;
    }

    @Override
    public String toString() {
        return "AvgSalary{" +
                "department='" + department + '\'' +
                ", avgSalary=" + avgSalary +
                '}';
    }
}
