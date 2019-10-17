package io.swagger.pet.store.tests.framework.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RedirectConfig;
import io.restassured.config.SSLConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.swagger.petstore.GsonObjectMapper;

import java.lang.reflect.Type;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
public abstract class BaseEndpoint<Endpoint, Model> {

    protected Response response;

    protected abstract Type getModelType();

    public abstract Endpoint sendRequest();

    protected abstract int getSuccessStatusCode();

    protected RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder().setConfig(RestAssured.config()
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig().defaultObjectMapper(GsonObjectMapper.gson()))
                .redirect(RedirectConfig.redirectConfig().followRedirects(false))
                .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
                .addFilters(Arrays.asList(
                        new RequestLoggingFilter(),
                        new ResponseLoggingFilter()));
    }

    public Endpoint assertRequestSuccess() {
        return assertStatusCode(getSuccessStatusCode());
    }

    public Endpoint assertStatusCode(int statusCode) {
        checkForRequestNotNull();
        assertThat(response.getStatusCode()).as("Status code").isEqualTo(statusCode);
        return (Endpoint) this;
    }

    public Model getResponseAsModelObject() {
        checkForRequestNotNull();
        return (Model) response.as(getModelType());
    }

    protected void checkForRequestNotNull() {
        assertThat(response).as("Request response").isNotNull();
    }

}