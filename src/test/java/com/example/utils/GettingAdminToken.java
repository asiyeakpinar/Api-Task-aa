package com.example.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.UUID;

public class GettingAdminToken {

    private static String adminToken;
    private static Instant tokenExpirationTime;
    static String username=ConfigurationReader.getProperty("username");
    static String password=ConfigurationReader.getProperty("password");

    public static String GetAdminToken() {

        if (adminToken == null || isAdminTokenExpired()) {
            adminToken = generateAdminToken();
        }

        return adminToken;
    }

    private static String generateAdminToken() {

        Response response = Login.adminLogin( username,password );

        adminToken = response.jsonPath().getString("token");
        String tokenExpirationDate = response.jsonPath().getString("accountExpirationDate");
        tokenExpirationTime = Instant.parse(tokenExpirationDate);
        System.out.println("Generate admin token worked : " + adminToken);
       return adminToken;
    }


    private static boolean isAdminTokenExpired() {
        if (tokenExpirationTime == null) {
            return true;
        }
        Instant now = Instant.now();
        System.out.println("token Expiration time: " + tokenExpirationTime);
        return now.isAfter(tokenExpirationTime);
    }


}
