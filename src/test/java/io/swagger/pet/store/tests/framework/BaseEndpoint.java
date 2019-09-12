package io.swagger.pet.store.tests.framework;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RedirectConfig;
import io.restassured.config.SSLConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.swagger.petstore.GsonObjectMapper;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
public abstract class BaseEndpoint<T extends BaseEndpoint, M> {

    protected Response response;

    protected abstract Type getModelType();

    public abstract T sendRequest();

    protected RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder().setConfig(RestAssured.config()
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig().defaultObjectMapper(GsonObjectMapper.gson()))
                .redirect(RedirectConfig.redirectConfig().followRedirects(false))
                .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
                .addFilters(Arrays.asList(
                        new RequestLoggingFilter(),
                        new ResponseLoggingFilter()));
    }

    public T assertRequestSuccess() {
        return assertStatusCode(getSuccessStatusCode());
    }

    public T assertStatusCode(int statusCode) {
        checkForRequestNotNull();
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
        return (T) this;
    }

    public M getModel() {
        checkForRequestNotNull();
        return (M) response.as(getModelType());
    }

    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    protected void checkForRequestNotNull() {
        assertThat(response).as("Request response").isNotNull();
    }

}