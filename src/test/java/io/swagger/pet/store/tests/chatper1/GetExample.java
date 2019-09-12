package io.swagger.pet.store.tests.chatper1;

import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetExample {

    @Test
    public void getTest() {
        given()
                .log()
                .all()
                .filter(new ResponseLoggingFilter())
                .contentType("application/json")
                .accept("application/json")
                .pathParam("petId", "123")
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then().assertThat().statusCode(200).extract().response();
    }


}
