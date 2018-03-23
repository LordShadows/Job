package com.ivo.webservice.mvc.service;

import com.ivo.webservice.mvc.dao.DepartmentServiceDAO;
import com.ivo.webservice.mvc.model.Department;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/*************************************************
/** In-memory database model
*************************************************/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mvc-config.xml",
        "classpath:application-context.xml"})
public class DepartmentServiceDAOImplTest {

    @Autowired
    DepartmentServiceDAO departmentServiceDAO;

    @Test
    public void create() {
        Department department1 = new Department("Dep");

        boolean result = departmentServiceDAO.create(department1);
        Assert.assertTrue(result);

        int departmentIdCreated = 5;
        Department department2 = departmentServiceDAO.read(departmentIdCreated);
        Assert.assertEquals("Dep", department2.getName_dep());
    }

    @Test
    public void read() {
        Department resultDepartment = departmentServiceDAO.read(1);

        Assert.assertNotNull(resultDepartment);
        Assert.assertEquals(1, resultDepartment.getId());
    }

    @Test
    public void list() {
        // created, if does not exists
        Department department1 = new Department("Dep");
        boolean result = departmentServiceDAO.create(department1);
        Assert.assertTrue(result);

        // verify
        List<Department> departments = departmentServiceDAO.list();
        Assert.assertNotNull(departments);
        Assert.assertTrue(departments.size() > 0);
    }

    @Test
    public void update() {
        Department department1 = departmentServiceDAO.read(4);

        Assert.assertNotNull(department1);
        department1.setName_dep("Updated");

        boolean result = departmentServiceDAO.update(department1);
        Assert.assertTrue(result);

        int departmentIdUpdated = 4;
        Department department2 = departmentServiceDAO.read(departmentIdUpdated);
        Assert.assertEquals("Updated", department2.getName_dep());
    }

    @Test
    public void delete() {
        // created department, which does not exists fk_constraints
        Department department = new Department("DepDelete");
        boolean result = departmentServiceDAO.create(department);
        Assert.assertTrue(result);

        // read
        Department department1 = departmentServiceDAO.read(5);
        Assert.assertNotNull(department1);

        result = departmentServiceDAO.delete(department1.getId());
        Assert.assertTrue(result);

        // verify
        int departmentIdDeleted = 5;
        Department department2 = departmentServiceDAO.read(departmentIdDeleted);
        Assert.assertNull(department2);
    }

}
