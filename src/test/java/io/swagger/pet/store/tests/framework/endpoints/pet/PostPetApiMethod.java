package io.swagger.pet.store.tests.framework.endpoints.pet;

import io.swagger.pet.store.tests.framework.client.PetApiClient;
import io.swagger.petstore.model.Pet;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.function.Function;

public class PostPetApiMethod extends PetApiClient<PostPetApiMethod, Pet> {

    private Pet pet;

    @Override
    public PostPetApiMethod sendRequest() {
        response = getPetClient()
                .addPet()
                .body(pet)
                .execute(Function.identity());
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    @Override
    protected Type getModelType() {
        return Pet.class;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}