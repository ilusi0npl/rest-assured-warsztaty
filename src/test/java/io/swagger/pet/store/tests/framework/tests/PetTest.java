package io.swagger.pet.store.tests.framework.tests;

import io.swagger.pet.store.tests.framework.methods.pet.GetPetApiMethod;
import io.swagger.pet.store.tests.framework.methods.pet.PostPetApiMethod;
import io.swagger.petstore.model.Category;
import io.swagger.petstore.model.Pet;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.List;

import static io.swagger.petstore.model.Pet.StatusEnum.AVAILABLE;

public class PetTest {

    //https://petstore.swagger.io/

    @Test
    public void shouldGetPetWhenPetIsAvailable() {
        Pet pet = new GetPetApiMethod()
                .petId("123")
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        Assertions.assertThat(pet.getId()).as("Pet with specified ID does not exist").isEqualTo(123L);
    }

    @Test
    public void shouldCreatePetWhenCallPetEndpoint() {
        Pet petToBeAdded = createPet();

        Pet createdPet = new PostPetApiMethod()
                .pet(petToBeAdded)
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        Assertions.assertThat(createdPet.getId()).as("Pet with specified id was not created").isEqualTo(petToBeAdded.getId());
    }

    private Pet createPet() {
        return new Pet().id(TestUtils.nextId()).name("alex").status(AVAILABLE)
                .category(new Category().id(TestUtils.nextId()).name("dog"))
                .photoUrls(List.of("http://foo.bar.com/1"));
    }

}