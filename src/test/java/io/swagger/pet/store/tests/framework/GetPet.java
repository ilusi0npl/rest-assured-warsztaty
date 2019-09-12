package io.swagger.pet.store.tests.framework;

import io.swagger.petstore.model.Pet;

import java.lang.reflect.Type;
import java.util.function.Function;

public class GetPet extends PetEndpoint<GetPet, Pet> {

    private String petId;

    @Override
    public GetPet sendRequest() {
        response = getPetApi().getPetById().petIdPath(petId).execute(Function.identity());
        return this;
    }

    @Override
    protected Type getModelType() {
        return Pet.class;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }
}