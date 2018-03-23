package com.ivo.webservice.mvc.dao;


import com.ivo.webservice.mvc.model.Employee;

import java.sql.Date;
import java.util.List;

public interface EmployeeServiceDAO {

    public boolean create(Employee employee);

    public Employee read(int id);

    public List<Employee> list();

    public boolean update(Employee employee);

    public boolean delete(int id);

    public List<Employee> search(Date dateOfBirth);

    public List<Employee> searchBetweenDates(Date from, Date to);

}
