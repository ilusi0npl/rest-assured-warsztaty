package io.swagger.petstore.test.chapter2.json.path;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class JsonPathTest {

    //Remember about with $.
    //http://jsonpath.com/

    @Test
    public void jsonPathTest() {
        Response response = given()
                .log()
                .all()
                .contentType("application/json")
                .accept("application/json")
                .body("{\n" +
                        "  \"id\": 8315298304486418000,\n" +
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

        System.out.println("RESPONSE WAS");
        System.out.println(response.prettyPrint());
        long petId = response.jsonPath().getLong("id");

        given()
                .log()
                .all()
                .contentType("application/json")
                .accept("application/json")
                .pathParam("petId", petId)
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then().assertThat().statusCode(200);
    }


}
