package io.swagger.pet.store.tests.framework;

import io.swagger.petstore.client.PetApi;


public abstract class PetEndpoint<T extends BaseEndpoint, M> extends BaseEndpoint<T, M> {

    protected PetApi getPetApi() {
        return PetApi.pet(getRequestSpecBuilder().setBaseUri("http://petstore.swagger.io:80/v2"));
    }

}