package org.BasicExample;

import com.fasterxml.jackson.databind.util.JSONPObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class Exercises {

    private String url = "https://reqres.in/";
    //Basic validations using given
    @Test
    public void execise1(){
        baseURI = url;

        try {
            given().get("api/users?page=2").then().statusCode(201);
            given().get("api/users?page=2").then().body("page",is(2));
            System.out.println("Validation is OK");
        }catch(java.lang.AssertionError e){
            System.out.println("Validation is NOK");
        }
        //given().get("api/users?page=2").then().assertThat().body(matchesJsonSchemaInClasspath("products-schema.json"));
    }

    //post request example
    @Test
    public void exercise2(){
        baseURI = url + "api/register";
        String jsonString = "{" + "\"email\":\"eve.holt@reqres.in\"," + "\"password\":\"pistol\"" + "}";

        System.out.println(jsonString);

        RequestSpecification postRequest = RestAssured.given();

        postRequest.contentType(ContentType.JSON);

        postRequest.body(jsonString);

        Response response = postRequest.post();

        String responseString= response.prettyPrint();

        //System.out.println(responseString);

        ValidatableResponse validatableResponse = response.then();

        validatableResponse.statusCode(200);

        validatableResponse.body("id",equalTo(4));
        validatableResponse.body("token", equalTo("QpwL5tke4Pnpja7X4"));

    }

    //post request example in bdd format
    @Test
    public void exercise3(){
        String jsonString = "{" + "\"email\":\"eve.holt@reqres.in\"," + "\"password\":\"pistol\"" + "}";
        ValidatableResponse validatableResponse;

        //given
        validatableResponse = given()
                .baseUri("https://reqres.in/")
                .contentType(ContentType.JSON)
                .body(jsonString)
                //when
                .when()
                    .post("api/register")
                .then()
                .assertThat().statusCode(200).body("id",equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));

        System.out.println(validatableResponse.extract().asPrettyString());
    }

    //put request example
    @Test
    public void exercise4(){
        String jsonString = "{\"name\":\"morpheus\",\"job\":\"zion resident\"}";

        System.out.println(jsonString);

        baseURI = url + "api/users/2";

        RequestSpecification requestSpecification = RestAssured.given();

        requestSpecification.contentType(ContentType.JSON);

        requestSpecification.body(jsonString);

        Response response = requestSpecification.put();

        String responseString = response.prettyPrint();

        ValidatableResponse validatableResponse = response.then();

        validatableResponse.statusCode(200);

        validatableResponse.body("name",equalTo("morpheus"));

        validatableResponse.body("job",equalTo("zion resident"));

        //System.out.println(validatableResponse.extract().asPrettyString());
    }

    //Delete Request Example
    @Test
    public void exercise5(){
        RequestSpecification requestSpecification;
        Response response;
        ValidatableResponse validatableResponse;

        baseURI = url + "api/users/2";

        requestSpecification = RestAssured.given();
        response =  requestSpecification.delete("api/users/2");

        String resString = response.prettyPrint();

        validatableResponse =  response.then();

        validatableResponse.statusCode(204);

        System.out.println("The validation is OK");

    }

    //Delete using BDD format
    @Test
    public void exercise6(){
        ValidatableResponse validatableResponse;

        validatableResponse= given()
                .baseUri("https://reqres.in/api/users/2")
                .contentType(ContentType.JSON)
                            .when()
                .delete()
                            .then()
                .assertThat().statusCode(204);
        System.out.println("The validation is OK");
    }

    //post request example using Json Object
    @Test
    public void exercise7(){
        JSONObject mydata = new JSONObject();

        mydata.put("email","eve.holt@reqres.in");
        mydata.put("password","pistol");

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(mydata.toString())
                    //.log().all()
                .when()
                    .post("https://reqres.in/api/register")
                .then()
                    .assertThat().statusCode(200)
                    .body("id",equalTo(4))
                    .body("token",equalTo("QpwL5tke4Pnpja7X4"))
                .log().all();
    }

    //POST example using

}
