package io.swagger.pet.store.tests.chapter4.employee;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.swagger.pet.store.tests.chapter4.client.PetStoreClient;
import io.swagger.petstore.model.Pet;

import static io.swagger.petstore.ResponseSpecBuilders.shouldBeCode;
import static io.swagger.petstore.ResponseSpecBuilders.validatedWith;
import static org.apache.http.HttpStatus.SC_OK;

public class Employee {

    @Step("Adding Pet {pet}")
    public Response addAddPet(Pet pet) {
        return new PetStoreClient().getClient()
                .pet()
                .addPet()
                .body(pet)
                .execute(validatedWith(shouldBeCode(SC_OK)));
    }

    @Step("Getting pet by {pet}")
    public Pet getPet(Pet pet) {
        return new PetStoreClient().getClient()
                .pet()
                .getPetById()
                .petIdPath(pet.getId()).executeAs(validatedWith(shouldBeCode(SC_OK)));
    }

}
