package io.swagger.pet.store.tests.framework;

import io.swagger.pet.store.tests.chapter3.TestUtils;
import io.swagger.petstore.client.PetApi;
import io.swagger.petstore.model.Category;
import io.swagger.petstore.model.Pet;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.swagger.petstore.model.Pet.StatusEnum.AVAILABLE;

public class PetTest {

    @Test
    public void getPetTest() {
        GetPet getPet = new GetPet();
        getPet.setPetId("123");
        getPet.sendRequest();
        getPet.assertStatusCode(200);
    }

    private Pet getPet() {
        return new Pet().id(TestUtils.nextId()).name("alex").status(AVAILABLE)
                .category(new Category().id(TestUtils.nextId()).name("dog"))
                .photoUrls(Arrays.asList("http://foo.bar.com/1"));
    }

}