package io.swagger.petstore.test.chapter1.basic.http;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetExample {

    @Test
    public void getTest() {
        Response response = given()
                .log()
                .all()
                .contentType("application/json")
                .accept("application/json")
                .pathParam("petId", "123")
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println("RESPONSE WAS");
        System.out.println(response.prettyPrint());
    }


}
