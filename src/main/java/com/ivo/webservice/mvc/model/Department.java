package com.ivo.webservice.mvc.model;

public class Department {

    private int id;
    private String name_dep;

    public Department() {
    }

    public Department(String name_dep) {
        this.name_dep = name_dep;
    }

    public Department(int id, String name_dep) {
        this.id = id;
        this.name_dep = name_dep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_dep() {
        return name_dep;
    }

    public void setName_dep(String name_dep) {
        this.name_dep = name_dep;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name_dep='" + name_dep + '\'' +
                '}';
    }
}
