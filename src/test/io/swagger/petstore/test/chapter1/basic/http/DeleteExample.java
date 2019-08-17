package io.swagger.petstore.test.chapter1.basic.http;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteExample {

    @Test
    public void deleteTest() {
        Response response = given()
                .log()
                .all()
                .contentType("application/json")
                .accept("application/json")
                .pathParam("petId", "123")
                .delete("https://petstore.swagger.io/v2/pet/{petId}")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println("RESPONSE WAS");
        System.out.println(response.prettyPrint());
    }


}
