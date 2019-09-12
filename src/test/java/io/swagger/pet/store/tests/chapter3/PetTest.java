package io.swagger.pet.store.tests.chapter3;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.swagger.petstore.ApiClient;
import io.swagger.petstore.client.PetApi;
import io.swagger.petstore.model.Category;
import io.swagger.petstore.model.Pet;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.swagger.petstore.GsonObjectMapper.gson;
import static io.swagger.petstore.ResponseSpecBuilders.shouldBeCode;
import static io.swagger.petstore.ResponseSpecBuilders.validatedWith;
import static io.swagger.petstore.model.Pet.StatusEnum.*;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsIterableContaining.hasItem;

public class PetTest {

    private PetApi api;

    @BeforeMethod
    public void createApi() {
        ApiClient.Config config = ApiClient.Config.apiConfig();

        config.reqSpecSupplier(() -> {
            RestAssuredConfig restAssuredConfig = new RestAssuredConfig();

            LogConfig logConfig = new LogConfig();
            logConfig.enableLoggingOfRequestAndResponseIfValidationFails();
            logConfig.enablePrettyPrinting(true);

            restAssuredConfig.logConfig(logConfig);
            restAssuredConfig.objectMapperConfig(objectMapperConfig().defaultObjectMapper(gson()));

            RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
            requestSpecBuilder.setConfig(restAssuredConfig);
            requestSpecBuilder.log(LogDetail.ALL);
            requestSpecBuilder.setBaseUri("http://petstore.swagger.io:80/v2");
            return requestSpecBuilder;
        });
        api = ApiClient.api(config).pet();
    }

    @Test
    public void statusListTest() {
        Pet pet = getPet();
        api.addPet().body(pet.status(PENDING)).execute(validatedWith(shouldBeCode(SC_OK)));
        Response response = api
                .findPetsByStatus()
                .reqSpec(requestSpecBuilder -> requestSpecBuilder.addQueryParam("status", "PENDING"))
//                .statusQuery(PENDING)
                .execute(validatedWith(shouldBeCode(SC_OK)));
        List<Long> petsId = Arrays.stream(response.then().extract().body().as(io.swagger.main.pojo.Pet[].class))
                .map(io.swagger.main.pojo.Pet::getId).collect(Collectors.toList());

        assertThat(petsId, hasItem(pet.getId()));
        api.deletePet().petIdPath(pet.getId()).execute(validatedWith(shouldBeCode(SC_OK)));
    }

    @Test
    public void getPetByIdTest() {
        Pet pet = getPet();
        api.addPet().body(pet.status(PENDING)).execute(validatedWith(shouldBeCode(SC_OK)));
        Pet fetchedPet = api.getPetById()
                .petIdPath(pet.getId()).executeAs(validatedWith(shouldBeCode(SC_OK)));
        assertThat(fetchedPet.getId(), equalTo(pet.getId()));
        api.deletePet().petIdPath(pet.getId()).execute(validatedWith(shouldBeCode(SC_OK)));
    }

    @Test(priority = 9999)
    public void deletePetTest() {
        Pet pet = getPet();
        api.addPet().body(pet.status(PENDING)).execute(validatedWith(shouldBeCode(SC_OK)));
        api.deletePet().petIdPath(pet.getId()).execute(validatedWith(shouldBeCode(SC_OK)));
        api.getPetById()
                .petIdPath(pet.getId()).execute(validatedWith(shouldBeCode(SC_NOT_FOUND)));
    }

    @Test
    public void uploadFileTest() throws IOException {
        Pet pet = getPet();
        api.addPet().body(pet).execute(validatedWith(shouldBeCode(SC_OK)));
        File file = new File("hello.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("Hello world!");
        writer.close();
        api.uploadFile().fileMultiPart(file)
                .petIdPath(pet.getId()).execute(validatedWith(shouldBeCode(SC_OK)));
        api.deletePet().petIdPath(pet.getId()).execute(validatedWith(shouldBeCode(SC_OK)));
    }

    @Test
    public void updatePetTest() {
        Pet pet = getPet();
        api.addPet().body(pet).execute(validatedWith(shouldBeCode(SC_OK)));
        api.updatePet().body(pet.status(SOLD)).execute(validatedWith(shouldBeCode(SC_OK)));
        Pet.StatusEnum statusEnum = api.getPetById().petIdPath(pet.getId()).executeAs(validatedWith(shouldBeCode(SC_OK))).getStatus();
        assertThat(statusEnum, equalTo(SOLD));
        api.deletePet().petIdPath(pet.getId()).execute(validatedWith(shouldBeCode(SC_OK)));
    }


    private Pet getPet() {
        return new Pet().id(TestUtils.nextId()).name("alex").status(AVAILABLE)
                .category(new Category().id(TestUtils.nextId()).name("dog"))
                .photoUrls(Arrays.asList("http://foo.bar.com/1"));
    }

}