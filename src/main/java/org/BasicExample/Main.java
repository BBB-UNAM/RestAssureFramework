package org.BasicExample;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        Response res = RestAssured.get("https://reqres.in/api/users?page=2");

        System.out.println(res.getStatusCode());
        System.out.println(res.getTime());
        System.out.println(res.getContentType());
        System.out.println(res.getBody().asString());
        System.out.println(res.getStatusLine());

        int statusCode = res.getStatusCode();
        Assert.assertEquals(statusCode,200,"The status code response is NOK");

    }
}