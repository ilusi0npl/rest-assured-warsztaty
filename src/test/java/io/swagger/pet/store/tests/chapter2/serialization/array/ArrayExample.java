package io.swagger.pet.store.tests.chapter2.serialization.array;

import io.restassured.filter.log.ResponseLoggingFilter;
import io.swagger.pet.store.tests.chapter2.serialization.single.object.pojo.Pet;
import org.testng.annotations.Test;


import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class ArrayExample {

    @Test
    public void listExample() {
        Pet[] arrayOfPets = given()
                .log()
                .all()
                .filter(new ResponseLoggingFilter())
                .contentType("application/json")
                .accept("application/json")
                .queryParam("status", "sold")
                .get("https://petstore.swagger.io/v2/pet/findByStatus")
                .then().assertThat().statusCode(200).extract().response().as(Pet[].class);

        Arrays.stream(arrayOfPets).forEach(System.out::println);
    }

}
