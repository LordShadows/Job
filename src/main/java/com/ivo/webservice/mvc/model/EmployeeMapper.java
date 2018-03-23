package com.ivo.webservice.mvc.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("IDEMPLOYEES"));
        employee.setFio(rs.getString("FIO"));
        employee.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
        employee.setSalary(rs.getInt("SALARY"));
        employee.setDepartment(rs.getString("NAME_DEP"));
        return employee;
    }
}
