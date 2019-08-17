package io.swagger.pet.store.tests.chapter4.client;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.swagger.petstore.ApiClient;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.swagger.petstore.ApiClient.Config;
import static io.swagger.petstore.ApiClient.api;
import static io.swagger.petstore.GsonObjectMapper.gson;

public class PetStoreClient {

    @Step("Get Pet Store Client")
    public ApiClient getClient() {
        Config config = Config.apiConfig();

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
            requestSpecBuilder.addFilter(new AllureRestAssured());
            requestSpecBuilder.addFilter(new ErrorLoggingFilter());
            requestSpecBuilder.setBaseUri("http://petstore.swagger.io:80/v2");
            return requestSpecBuilder;
        });
        return api(config);
    }
}
