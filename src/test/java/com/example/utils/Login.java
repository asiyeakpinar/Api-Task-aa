package com.example.utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Login {

    private static final String BASE_URL = ConfigurationReader.getProperty("url");

    public static Response adminLogin( String username, String password ) {

        String loginInfoRequestBody ="{\"username\":\""+username+"\"" +
                ",\"password\":\""+password+"\"}";


        return given().contentType(ContentType.JSON).body(loginInfoRequestBody).
                when().post(BASE_URL + "/hr/login");


    }
}
