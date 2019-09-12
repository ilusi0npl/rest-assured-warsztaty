package io.swagger.pet.store.tests.chapter2.assertions.assertj.specific.generated;

import io.swagger.main.pojo.Pet;
import io.swagger.pet.store.tests.generated.assertions.BddAssertions;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.swagger.pet.store.tests.generated.assertions.Assertions.assertThat;

public class GeneratedAssertionsExample {

    @Test
    public void generatedAssertionsTest() {
        Pet createdPet = new Pet();
        createdPet.setId(123123L);
        createdPet.setName("Thorgal");
        createdPet.setStatus("available");

        given()
                .log()
                .all()
                .contentType("application/json")
                .accept("application/json")
                .body(createdPet)
                .post("https://petstore.swagger.io/v2/pet")
                .then().assertThat().statusCode(200);

        Pet getPet = given()
                .log()
                .all()
                .contentType("application/json")
                .accept("application/json")
                .pathParam("petId", createdPet.getId())
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then().assertThat().statusCode(200).extract().body().as(Pet.class);

        assertThat(getPet)
                .hasId(createdPet.getId())
                .hasName(createdPet.getName())
                .hasStatus(createdPet.getStatus());

        BddAssertions.then(getPet)
                .hasId(createdPet.getId())
                .hasName(createdPet.getName())
                .hasStatus(createdPet.getStatus());



    }


}
