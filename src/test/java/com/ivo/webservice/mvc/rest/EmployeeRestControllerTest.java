package com.ivo.webservice.mvc.rest;

import com.ivo.webservice.mvc.model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mvc-config.xml",
        "classpath:application-context.xml"})
public class EmployeeRestControllerTest {

    @Autowired
    RestTemplate restTemplate;

    private final String REST_URL = "http://localhost:8080/employees/";
    private final String HTTP_NOT_FOUND = "404 Not Found";
    private final String HTTP_BAD_REQUEST = "400 Bad Request";

    @Test
    public void testList() throws Exception {
        ResponseEntity<Employee[]> responseEntity = restTemplate.getForEntity(
                REST_URL,
                Employee[].class);

        int status = responseEntity.getStatusCode().value();
        Employee[] resultEmployees = responseEntity.getBody();

        // verify
        Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

        Assert.assertNotNull(resultEmployees);
        Assert.assertTrue(resultEmployees.length > 0);
    }

    @Test
    public void testCreate() throws Exception {
        // prepare
        Employee employee = new Employee();
        employee.setFio("Test Fio");
        Date date = Date.valueOf("1990-01-01");
        employee.setDateOfBirth(date);
        employee.setSalary(600);
        employee.setDepartment("Sales");

        HttpEntity<Employee> requestEntity = new HttpEntity<Employee>(employee);

        // execute
        ResponseEntity<Employee> responseEntity = restTemplate.postForEntity(
                REST_URL + "/create",
                employee,
                Employee.class);

        // collect Response
        int status = responseEntity.getStatusCode().value();
        Employee resultEmployee = responseEntity.getBody();

        // verify
        Assert.assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);

        Assert.assertNotNull(resultEmployee);
        Assert.assertNotNull(resultEmployee.getId());
    }

    @Test
    public void testPositiveDelete() throws Exception {
        int employeeIdToDelete = 5;

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                REST_URL + "/delete/" + "{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                employeeIdToDelete);

        // verify
        int status = responseEntity.getStatusCode().value();
        Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
    }

    @Test
    public void testNegativeDelete() throws Exception {
        int employeeIdToDelete = 400;

        try {
            ResponseEntity<Void> responseEntity = restTemplate.exchange(
                    REST_URL + "/delete/" + "{id}",
                    HttpMethod.DELETE,
                    null,
                    Void.class,
                    employeeIdToDelete);
        } catch (RestClientException ex) {
            // verify
            Assert.assertEquals("Incorrect Response Status", HTTP_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    public void testPositiveRead() throws Exception {
        int employeeIdToRead = 1;

        ResponseEntity<Employee> responseEntity = restTemplate.getForEntity(
                REST_URL + "{id}",
                Employee.class,
                employeeIdToRead);

        int status = responseEntity.getStatusCode().value();
        Employee resultEmployee = responseEntity.getBody();

        // verify
        Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

        Assert.assertNotNull(resultEmployee);
        Assert.assertEquals(employeeIdToRead, resultEmployee.getId());
    }

    @Test
    public void testNegativeRead() throws Exception {
        int employeeIdToRead = 800;

        try {
            ResponseEntity<Employee> responseEntity = restTemplate.getForEntity(
                    REST_URL + "{id}",
                    Employee.class,
                    employeeIdToRead);
        } catch (RestClientException ex) {
            // verify
            Assert.assertEquals("Incorrect Response Status", HTTP_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    public void testPositiveUpdate() {
        int employeeId = 4;

        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFio("TestEmployeeUpdate");
        Date date = Date.valueOf("1990-01-01");
        employee.setDateOfBirth(date);
        employee.setSalary(600);
        employee.setDepartment("Sales");

        HttpEntity<Employee> requestEntity = new HttpEntity<Employee>(employee);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                REST_URL + "/update",
                HttpMethod.PUT,
                requestEntity,
                Void.class);

        // verify
        int status = responseEntity.getStatusCode().value();
        Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
    }

    @Test
    public void testNegativeUpdate() {
        int employeeIdUpdate = 500;

        Employee employee = new Employee();
        employee.setId(employeeIdUpdate);
        employee.setFio("TestEmployeeUpdate");
        Date date = Date.valueOf("1990-01-01");
        employee.setDateOfBirth(date);
        employee.setSalary(600);
        employee.setDepartment("Sales");

        HttpEntity<Employee> requestEntity = new HttpEntity<Employee>(employee);

        try {
            ResponseEntity<Void> responseEntity = restTemplate.exchange(
                    REST_URL + "/update",
                    HttpMethod.PUT,
                    requestEntity,
                    Void.class);
        } catch (RestClientException ex) {
            Assert.assertEquals("Incorrect Response Status", HTTP_NOT_FOUND, ex.getMessage());
        }

    }

    @Test
    public void testPositiveSearch() {
        Date dateOfBirth = Date.valueOf("1990-01-01");

        ResponseEntity<Employee[]> responseEntity = restTemplate.getForEntity(
                REST_URL + "/search/" + "{dateOfBirth}",
                Employee[].class,
                dateOfBirth);

        int status = responseEntity.getStatusCode().value();
        Employee[] resultEmployees = responseEntity.getBody();

        // verify
        Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

        Assert.assertNotNull(resultEmployees);
        Assert.assertTrue(resultEmployees.length > 0);
    }

    @Test
    public void testNegativeSearch() {
        String incorrectRequest = "value";

        try {
            ResponseEntity<Employee[]> responseEntity = restTemplate.getForEntity(
                    REST_URL + "/search/" + "{dateOfBirth}",
                    Employee[].class,
                    incorrectRequest);
        } catch (RestClientException ex) {
            // verify
            Assert.assertEquals("Incorrect Response Status", HTTP_BAD_REQUEST, ex.getMessage());
        }
    }

    @Test
    public void testPositiveSearchBetweenDates() {
        Date from = Date.valueOf("1980-01-01");
        Date to = Date.valueOf("2000-01-01");

        ResponseEntity<Employee[]> responseEntity = restTemplate.getForEntity(
                REST_URL + "/search" + "/{from}/" + "/{to}",
                Employee[].class,
                from, to);

        int status = responseEntity.getStatusCode().value();
        Employee[] resultEmployees = responseEntity.getBody();

        // verify
        Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

        Assert.assertNotNull(resultEmployees);
        Assert.assertTrue(resultEmployees.length > 0);
    }

    @Test
    public void testNegativeSearchBetweenDates() {
        Date correctDateFirst = Date.valueOf("2000-01-01");
        String incorrectRequest = "";

        try {
            ResponseEntity<Employee[]> responseEntity = restTemplate.getForEntity(
                    REST_URL + "/search" + "/{from}/" + "/{to}",
                    Employee[].class,
                    correctDateFirst, incorrectRequest);
        } catch (RestClientException ex) {
            // verify
            Assert.assertEquals("Incorrect Response Status", HTTP_BAD_REQUEST, ex.getMessage());
        }
    }

}
