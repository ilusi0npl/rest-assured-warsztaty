package io.swagger.pet.store.tests.framework.methods.pet;

import io.qameta.allure.Step;
import io.swagger.pet.store.tests.framework.client.PetApiClient;
import io.swagger.petstore.model.Pet;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.function.Function;

@Accessors(fluent = true)
public class GetPetApiMethod extends PetApiClient<GetPetApiMethod, Pet> {

    @Setter
    private String petId;

    @Step("GET Pet")
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

}