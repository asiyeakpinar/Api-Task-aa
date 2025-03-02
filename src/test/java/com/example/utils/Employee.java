package com.example.utils;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Employee {
    private static final String BASE_URL = ConfigurationReader.getProperty("url");
    public static String addEmployeeRequestBody;

    public static Response addEmployee(String adminToken) {
     addEmployeeRequestBody =generateEmployeeData();
       return given().
                contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(addEmployeeRequestBody)
                .when().post(BASE_URL + "/employees");

   }
    private static final Faker faker = new Faker();
    public static String generateEmployeeData() {
        return String.format(
                "{ \"firstName\": \"%s\", \"lastName\": \"%s\", \"dateOfBirth\": \"%d-%02d-%02d\", " +
                        "\"contactInfo\": { \"email\": \"%s\", \"phone\": \"%s\", " +
                        "\"address\": { \"street\": \"%s\", \"town\": \"%s\", \"postCode\": \"%s\" } } }",
                faker.name().firstName(),
                faker.name().lastName(),
                faker.number().numberBetween(1950, 2010),
                faker.number().numberBetween(1, 12),
                faker.number().numberBetween(1, 28),
                faker.internet().emailAddress(),
                faker.phoneNumber().cellPhone(),
                faker.address().streetAddress(),
                faker.address().city(),
                faker.address().zipCode()
        );
    }
}
