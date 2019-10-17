package io.swagger.pet.store.tests.framework.client;

import io.swagger.pet.store.tests.framework.base.BaseEndpoint;
import io.swagger.petstore.client.PetApi;


public abstract class PetApiClient<Endpoint, Model> extends BaseEndpoint<Endpoint, Model> {

    protected PetApi getPetClient() {
        return PetApi.pet(getRequestSpecBuilder().setBaseUri("http://petstore.swagger.io:80/v2"));
    }

}