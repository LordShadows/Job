package com.ivo.webservice.mvc.rest;


import com.ivo.webservice.mvc.dao.DepartmentServiceDAO;
import com.ivo.webservice.mvc.model.AvgSalary;
import com.ivo.webservice.mvc.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = { "http://localhost:8084" })
@RestController
@RequestMapping(value = "/departments")
public class DepartmentRestController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentRestController.class);

    @Autowired
    DepartmentServiceDAO departmentServiceDao;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Department>> list() {
        List<Department> departments = departmentServiceDao.list();
        if (departments.isEmpty()) {
            logger.debug("Department does not exists");
            return new ResponseEntity<List<Department>>(HttpStatus.NO_CONTENT);
        }
        logger.debug("Found " + departments.size() + " departments");
        logger.debug(Arrays.toString(departments.toArray()));
        return new ResponseEntity<List<Department>>(departments, HttpStatus.OK);
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Department> read(@PathVariable(value = "departmentId") int departmentId) {
        Department department = departmentServiceDao.read(departmentId);
        if (department == null) {
            logger.debug("Department with id " + department + " does not exists");
            return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Found Department:: " + department);
        return new ResponseEntity<Department>(department, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Department> create(@RequestBody Department department) {
        departmentServiceDao.create(department);
        logger.debug("Added: " + department);
        return new ResponseEntity<Department>(department, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Void> update(@RequestBody Department department) {
        Department existingDep = departmentServiceDao.read(department.getId());
        if (existingDep == null) {
            logger.debug("Department with id " + department.getId() + " does not exists");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            departmentServiceDao.update(department);
            logger.debug("Updated: " + department);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/delete/{departmentId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(value = "departmentId") int departmentId) {
        Department department = departmentServiceDao.read(departmentId);
        if (department == null) {
            logger.debug("Department with id " + department + " does not exists");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            departmentServiceDao.delete(departmentId);
            logger.debug("Department with id " + departmentId + " deleted");
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/averageSalary", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<AvgSalary>> averageSalary() {
        List<AvgSalary> salaries = departmentServiceDao.averageSalaryByDepartment();
        if (salaries.isEmpty()) {
            logger.debug("Salaryies by department does not exists");
            return new ResponseEntity<List<AvgSalary>>(HttpStatus.NO_CONTENT);
        }
        logger.debug("Found " + salaries.size() + " salaries by departments");
        logger.debug(Arrays.toString(salaries.toArray()));
        return new ResponseEntity<List<AvgSalary>>(salaries, HttpStatus.OK);
    }

}
