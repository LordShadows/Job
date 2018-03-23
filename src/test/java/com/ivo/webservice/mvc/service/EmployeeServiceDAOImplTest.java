package com.ivo.webservice.mvc.service;


import com.ivo.webservice.mvc.dao.EmployeeServiceDAO;
import com.ivo.webservice.mvc.model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.List;

/*************************************************
 /** In-memory database model
 *************************************************/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mvc-config.xml",
        "classpath:application-context.xml"})
public class EmployeeServiceDAOImplTest {

    @Autowired
    EmployeeServiceDAO employeeServiceDAO;

    public Employee createEmployee(String fio) {
        Employee emp = new Employee();
        emp.setFio(fio);
        emp.setDateOfBirth(Date.valueOf("1990-01-01"));
        emp.setSalary(400);
        emp.setDepartment("Sales");
        return emp;
    }

    @Test
    public void create() {
        Employee employee1 = createEmployee("create");

        boolean result = employeeServiceDAO.create(employee1);
        Assert.assertTrue(result);

        int empIdCreated = 6;
        Employee employee2 = employeeServiceDAO.read(empIdCreated);
        Assert.assertEquals("create", employee2.getFio());
    }

    @Test
    public void read() {
        Employee employee = employeeServiceDAO.read(1);

        Assert.assertNotNull(employee);
        Assert.assertEquals(1, employee.getId());
    }

    @Test
    public void list() {
        // created, if does not exists
        Employee employee1 = createEmployee("list");
        boolean result = employeeServiceDAO.create(employee1);
        Assert.assertTrue(result);

        // verify
        List<Employee> employees = employeeServiceDAO.list();
        Assert.assertNotNull(employees);
        Assert.assertTrue(employees.size() > 0);
    }

    @Test
    public void update() {
        Employee employee1 = employeeServiceDAO.read(6);

        Assert.assertNotNull(employee1);
        employee1.setFio("Updated");

        boolean result = employeeServiceDAO.update(employee1);
        Assert.assertTrue(result);

        int empIdUpdated = 6;
        Employee employee2 = employeeServiceDAO.read(empIdUpdated);
        Assert.assertEquals("Updated", employee2.getFio());
    }

    @Test
    public void delete() {
        // created department, which does not exists fk_constraints
        Employee employee = createEmployee("deleted");
        boolean result = employeeServiceDAO.create(employee);
        Assert.assertTrue(result);

        // read
        Employee employee1 = employeeServiceDAO.read(5);
        Assert.assertNotNull(employee1);

        result = employeeServiceDAO.delete(employee1.getId());
        Assert.assertTrue(result);

        // verify
        int empIdDeleted = 5;
        Employee employee2 = employeeServiceDAO.read(empIdDeleted);
        Assert.assertNull(employee2);
    }

}