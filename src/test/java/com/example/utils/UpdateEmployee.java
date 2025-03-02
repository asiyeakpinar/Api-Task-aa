package com.example.utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UpdateEmployee {
    private static final String BASE_URL = ConfigurationReader.getProperty("url");

    public static Response updateEmployee(String adminToken,String employeeId) {

        String addEmployeeRequestBody =Employee.generateEmployeeData();

        return given().
                contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(addEmployeeRequestBody)
                .when().put(BASE_URL + "/employees/" + employeeId);


    }
}
