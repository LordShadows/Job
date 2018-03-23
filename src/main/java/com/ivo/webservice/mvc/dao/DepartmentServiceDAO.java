package com.ivo.webservice.mvc.dao;


import com.ivo.webservice.mvc.model.AvgSalary;
import com.ivo.webservice.mvc.model.Department;

import java.util.List;

public interface DepartmentServiceDAO {

    public boolean create(Department department);

    public Department read(int id);

    public List<Department> list();

    public boolean update(Department department);

    public boolean delete(int id);

    public List<AvgSalary> averageSalaryByDepartment();

}
