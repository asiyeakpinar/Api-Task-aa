package com.example.tests;

import com.example.utils.ConfigurationReader;
import com.example.utils.GettingAdminToken;
import com.example.utils.Login;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {



    @Test
    public void loginWithValidCredentials()  {
        String username=ConfigurationReader.getProperty("username");
        String password=ConfigurationReader.getProperty("password");

        Response response= Login.adminLogin(username, password);
        String token= response.jsonPath().getString("token");
        assertEquals(200, response.statusCode(), "Status code is not 200");
        assertNotNull(token, "Token is missing");
        assertFalse(token.isEmpty(), "Token is empty");



    }
    @Test
    public void loginWithInValidCredentials()  {
        String username=ConfigurationReader.getProperty("invalidUsername");
        String password=ConfigurationReader.getProperty("invalidPassword");

        Response response= Login.adminLogin(username, password);

        String token= response.jsonPath().getString("token");
        assertEquals(401, response.statusCode(), "Status code is not 401");
        assertNull(token, "Token is available");
        assertEquals("Invalid credentials",response.jsonPath().getString("error"),
                "Error message mismatch");





    }





}
