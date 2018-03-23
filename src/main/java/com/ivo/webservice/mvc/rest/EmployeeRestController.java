package com.ivo.webservice.mvc.rest;

import com.ivo.webservice.mvc.dao.EmployeeServiceDAO;
import com.ivo.webservice.mvc.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = { "http://localhost:8084" })
@RestController
@RequestMapping(value = "/employees")
public class EmployeeRestController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentRestController.class);

    @Autowired
    EmployeeServiceDAO employeeServiceDAO;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Employee>> list() {
        List<Employee> employees = employeeServiceDAO.list();
        if (employees.isEmpty()) {
            logger.debug("Employees does not exists");
            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
        }
        logger.debug("Found " + employees.size() + " Employees");
        logger.debug(Arrays.toString(employees.toArray()));
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Employee> read(@PathVariable(value = "employeeId") int employeeId) {
        Employee employee = employeeServiceDAO.read(employeeId);
        if (employee == null) {
            logger.debug("Employee with id " + employeeId + " does not exists");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Found Employee:: " + employee);
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        employeeServiceDAO.create(employee);
        logger.debug("Added: " + employee);
        return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Void> update(@RequestBody Employee employee) {
        Employee existingDep = employeeServiceDAO.read(employee.getId());
        if (existingDep == null) {
            logger.debug("Employee with id " + employee.getId() + " does not exists");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            employeeServiceDAO.update(employee);
            logger.debug("Updated: " + employee);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/delete/{employeeId}", method = RequestMethod.DELETE)
    public  ResponseEntity<Void> delete(@PathVariable(value = "employeeId") int employeeId) {
        Employee department = employeeServiceDAO.read(employeeId);
        if (department == null) {
            logger.debug("Employee with id " + employeeId + " does not exists");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            employeeServiceDAO.delete(employeeId);
            logger.debug("Employee with id " + employeeId + " deleted");
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/search/{dateOfBirth}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Employee>> search(@PathVariable(value = "dateOfBirth") Date dateOfBirth) {
        List<Employee> employees = employeeServiceDAO.search(dateOfBirth);
        if (employees.isEmpty()) {
            logger.debug("Employees does not exists");
            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
        }
        logger.debug("Found " + employees.size() + " Employees");
        logger.debug(Arrays.toString(employees.toArray()));
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @RequestMapping(value = "/search/{from}/{to}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Employee>> searchBetweenDates(@PathVariable(value = "from") Date from,
                                                             @PathVariable(value = "to") Date to) {
        List<Employee> employees = employeeServiceDAO.searchBetweenDates(from, to);
        if (employees.isEmpty()) {
            logger.debug("Employees does not exists");
            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
        }
        logger.debug("Found " + employees.size() + " Employees");
        logger.debug(Arrays.toString(employees.toArray()));
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

}
