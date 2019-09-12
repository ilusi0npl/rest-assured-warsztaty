package io.swagger.pet.store.tests.chapter2.serialization.single.object;

import io.swagger.pet.store.tests.chapter2.serialization.single.object.pojo.Pet;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SingleObjectExample {

    @Test
    public void getTest() {
        given()
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
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .post("https://petstore.swagger.io/v2/pet")
                .then().assertThat().statusCode(200)
                .extract().response();

        Pet pet = given()
                .log()
                .all()
                .contentType("application/json")
                .accept("application/json")
                .pathParam("petId", "3123")
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then().assertThat().statusCode(200).extract().body().as(Pet.class);
    }


}
