package io.swagger.pet.store.tests.chatper1;

import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteExample {

    @Test
    public void deleteTest() {
        Response response = given()
                .log()
                .all()
                .filter(new ResponseLoggingFilter())
                .contentType("application/json")
                .accept("application/json")
                .pathParam("petId", "123")
                .delete("https://petstore.swagger.io/v2/pet/{petId}")
                .then().assertThat().statusCode(200).extract().response();
    }


}
