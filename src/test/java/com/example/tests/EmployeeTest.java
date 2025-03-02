package com.example.tests;

import com.example.utils.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class EmployeeTest {
    private static final String BASE_URL = ConfigurationReader.getProperty("url");
    private static String employeeId;
    private static String adminToken;
    private static String employeeRequestBody;

    @Test
    @Order(1)
    public void addEmployee() {
        adminToken = GettingAdminToken.GetAdminToken();

        Response response = Employee.addEmployee(adminToken);
        employeeRequestBody = Employee.addEmployeeRequestBody;

        assertEquals(201, response.getStatusCode(), "Employee creation failed");
        employeeId = response.jsonPath().getString("employeeId");

    }

    @Test
    @Order(2)
    public void addEmployeeWithDublicateEmail() {

        Response response = given().
                contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(employeeRequestBody)
                .when().post(BASE_URL + "/employees");
        System.out.println(employeeRequestBody  );

        assertEquals(400, response.getStatusCode(), "Employee creation succeeded," +
                "but it should fail due to duplicate email.");
    }

    @Test
    @Order(3)
    public void updateEmployee() {
        Response response = UpdateEmployee.updateEmployee(adminToken, employeeId);
        System.out.println(response.getBody().prettyPrint());
        assertEquals(200, response.getStatusCode(), "Employee creation succeeded");
        employeeRequestBody = Employee.generateEmployeeData();


    }

    @Test
    @Order(4)
    public void getEmployee() {
        Response response = given().
                contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .when().get(BASE_URL + "/employees/" + employeeId);
        System.out.println(employeeId );

        System.out.println(response.getBody().prettyPrint());
        assertEquals(200, response.getStatusCode(), "The employee was not found");
    }
    @Test
    @Order(5)
    public void getEmployeeWithoutToken() {
        Response response = given().
                contentType(ContentType.JSON)
                .when().get(BASE_URL + "/employees/" + employeeId);

        System.out.println("Response Content-Type: " + response.getHeader("Content-Type"));

        assertEquals(401, response.getStatusCode(),
                "Expected 401 Unauthorized, but got " + response.getStatusCode());

        assertEquals("Unauthorized", response.jsonPath().getString("message"),
                    "Actual message is: " + response.jsonPath().getString("message"));
        }


    @Test
    @Order(6)
    public void getEmployeeWithInvalidToken() {
        Response response = given().
                contentType(ContentType.JSON)
               .header("Authorization", "Bearer InvalidToken")
                .when().get(BASE_URL + "/employees/" + employeeId);

        System.out.println("Response Content-Type: " + response.getHeader("Content-Type"));

        assertEquals(401, response.getStatusCode(),
                "Expected 401 Unauthorized, but got " + response.getStatusCode());

        assertEquals("Unauthorized", response.jsonPath().getString("message"),
                "Expected message is  Unauthorized, but got " + response.jsonPath().getString("message"));


    }

    @Test
    @Order(7)
    public void deleteEmployee() {
        Response response = given().
                contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .when().delete(BASE_URL + "/employees/" + employeeId);
        assertEquals(200, response.getStatusCode(), "The employee was not found");
        assertEquals("Employee deleted successfully!", response.jsonPath().getString("message"),
                "The employee was not found");

    }
    @Test
    @Order(8)
    public void checkEmployeeExists() {
        Response response = given().
                contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .when().get(BASE_URL + "/employees/" + employeeId);
        assertEquals(404, response.getStatusCode(), "The employee has been found");
    }

}


