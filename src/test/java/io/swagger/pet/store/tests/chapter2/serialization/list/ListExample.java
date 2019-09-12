package io.swagger.pet.store.tests.chapter2.serialization.list;

import io.restassured.filter.log.ResponseLoggingFilter;
import io.swagger.pet.store.tests.chapter2.serialization.single.object.pojo.Pet;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ListExample {

    @Test
    public void arrayExample() {
        List<Pet> arrayOfPets = given()
                .log()
                .all()
                .filter(new ResponseLoggingFilter())
                .contentType("application/json")
                .accept("application/json")
                .queryParam("status", "sold")
                .get("https://petstore.swagger.io/v2/pet/findByStatus")
                .then().assertThat().statusCode(200).extract().response().body().jsonPath().getList(".", Pet.class);

        arrayOfPets.forEach(System.out::println);
    }

}
