package com.ivo.webservice.mvc.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper implements RowMapper<Department> {

    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("IDDEPARTMENTS"));
        department.setName_dep(rs.getString("NAME_DEP"));
        return department;
    }

}
