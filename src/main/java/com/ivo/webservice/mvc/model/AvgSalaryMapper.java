package com.ivo.webservice.mvc.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AvgSalaryMapper implements RowMapper<AvgSalary> {

    @Override
    public AvgSalary mapRow(ResultSet rs, int i) throws SQLException {
        AvgSalary avgSalary = new AvgSalary();
        avgSalary.setDepartment(rs.getString("NAME_DEP"));
        avgSalary.setAvgSalary(rs.getInt("AVG_SALARY"));
        return avgSalary;
    }
}
