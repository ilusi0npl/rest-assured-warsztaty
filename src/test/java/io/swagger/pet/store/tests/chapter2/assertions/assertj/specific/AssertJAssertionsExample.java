package io.swagger.pet.store.tests.chapter2.assertions.assertj.specific;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AssertJAssertionsExample {

    @Test
    public void getTest() {
        Response postResponse = given()
                .log()
                .all()
                .contentType("application/json")
                .accept("application/json")
                .body("{\n" +
                        "  \"id\": 3123,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"string\"\n" +
                        "  },\n" +
                        "  \"name\": \"Burek\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"something\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .extract().response();

        StatusCodeAssertions.assertThat(postResponse).statusCodeIsOk();

        Response getResponse = given()
                .log()
                .all()
                .contentType("application/json")
                .accept("application/json")
                .pathParam("petId", "3123")
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then()
                .extract().response();

        StatusCodeAssertions.assertThat(getResponse).statusCodeIsOk();

    }


}
