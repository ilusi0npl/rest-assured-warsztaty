package io.swagger.pet.store.tests.framework.endpoints.pet;

import io.swagger.pet.store.tests.framework.client.PetApiClient;
import io.swagger.petstore.model.Pet;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.function.Function;

public class GetPetApiMethod extends PetApiClient<GetPetApiMethod, Pet> {

    private String petId;

    @Override
    public GetPetApiMethod sendRequest() {
        response = getPetClient()
                .getPetById()
                .petIdPath(petId)
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

    public void setPetId(String petId) {
        this.petId = petId;
    }
}