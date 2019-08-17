package chapter2.assertions.basic;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class HamcrestAssertionsExample {

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
                        "      \"name\": \"something\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .post("https://petstore.swagger.io/v2/pet")
                .then().assertThat().statusCode(200)
                .extract().response();

        given()
                .log()
                .all()
                .contentType("application/json")
                .accept("application/json")
                .pathParam("petId", "3123")
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("id", equalTo(3123))
                .assertThat().body("name", equalTo("Burek"))
                .assertThat().body("tags[0].name", equalTo("something"));
    }


}
