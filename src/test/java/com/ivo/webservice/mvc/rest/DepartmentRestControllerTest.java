package com.ivo.webservice.mvc.rest;

import com.ivo.webservice.mvc.model.Department;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mvc-config.xml", "classpath:application-context.xml"})
public class DepartmentRestControllerTest {

    @Autowired
    RestTemplate restTemplate;

    private final String REST_URL = "http://localhost:8080/departments/";
    private final String HTTP_NOT_FOUND = "404 Not Found";

    @Test
    public void testList() throws Exception {
        ResponseEntity<Department[]> responseEntity = restTemplate.getForEntity(REST_URL, Department[].class);

        int status = responseEntity.getStatusCode().value();
        Department[] resultDepartments = responseEntity.getBody();

        // verify
        Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

        Assert.assertNotNull(resultDepartments);
        Assert.assertTrue(resultDepartments.length > 0);
    }

    @Test
    public void testCreate() throws Exception {
        // prepare
        Department department = new Department("TestCreatedDepartment");

        // execute
        ResponseEntity<Department> responseEntity = restTemplate.postForEntity(
                REST_URL + "/create",
                department,
                Department.class);

        // collect Response
        int status = responseEntity.getStatusCode().value();
        Department resultDepartment = responseEntity.getBody();

        // verify
        Assert.assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);

        Assert.assertNotNull(resultDepartment);
        Assert.assertNotNull(resultDepartment.getId());
    }

    @Test
    public void testPositiveDelete() throws Exception {
        int departmentIdToDelete = 4;

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                REST_URL + "/delete/" + "{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                departmentIdToDelete);

        // verify
        int status = responseEntity.getStatusCode().value();
        Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
    }

    @Test
    public void testNegativeDelete() throws Exception {
        int departmentIdToDelete = 400;

        try {
            ResponseEntity<Void> responseEntity = restTemplate.exchange(
                    REST_URL + "/delete/" + "{id}",
                    HttpMethod.DELETE,
                    null,
                    Void.class,
                    departmentIdToDelete);
        } catch (RestClientException ex) {
            // verify
            Assert.assertEquals("Incorrect Response Status", HTTP_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    public void testPositiveRead() throws Exception {
        int departmentIdToRead = 1;

        ResponseEntity<Department> responseEntity = restTemplate.getForEntity(
                REST_URL + "{id}",
                Department.class,
                departmentIdToRead);

        int status = responseEntity.getStatusCode().value();
        Department resultDepartment = responseEntity.getBody();

        // verify
        Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

        Assert.assertNotNull(resultDepartment);
        Assert.assertEquals(departmentIdToRead, resultDepartment.getId());
    }

    @Test
    public void testNegativeRead() throws Exception {
        int departmentIdToRead = 800;

        try {
            ResponseEntity<Department> responseEntity = restTemplate.getForEntity(
                    REST_URL + "{id}",
                    Department.class,
                    departmentIdToRead);
        } catch (RestClientException ex) {
            // verify
            Assert.assertEquals("Incorrect Response Status", HTTP_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    public void testPositiveUpdate() {
        int departmentIdCreated = 5;
        Department department = new Department(departmentIdCreated, "TestDepartmentUpdate");
        HttpEntity<Department> requestEntity = new HttpEntity<Department>(department);

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
        int departmentIdUpdate = 500;

        Department department = new Department(departmentIdUpdate, "TestDepartmentUpdate");
        HttpEntity<Department> requestEntity = new HttpEntity<Department>(department);

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

}
