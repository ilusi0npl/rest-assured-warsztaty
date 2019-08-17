package chapter2.assertions.assertj.specific;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.AbstractAssert;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.apache.http.HttpStatus.SC_CREATED;

public class StatusCodeAssertions extends AbstractAssert<StatusCodeAssertions, Response> {

    public StatusCodeAssertions(Response response) {
        super(response, StatusCodeAssertions.class);
    }

    public static StatusCodeAssertions assertThat(Response response) {
        return new StatusCodeAssertions(response);
    }

    public StatusCodeAssertions statusCodeIsOk() {
        isNotNullResponse();

        checkStatusCode(HttpStatus.SC_OK, getFailMessage("200 (OK)"));
        return this;
    }

    public StatusCodeAssertions statusCodeIsCreated() {
        isNotNullResponse();

        checkStatusCode(SC_CREATED, getFailMessage("201 (Created)"));
        return this;
    }

    public StatusCodeAssertions statusCodeIsAccepted() {
        isNotNullResponse();

        checkStatusCode(HttpStatus.SC_ACCEPTED, getFailMessage("202 (Accepted)"));
        return this;
    }

    public StatusCodeAssertions statusCodeIsNoContent() {
        isNotNullResponse();

        checkStatusCode(HttpStatus.SC_NO_CONTENT, getFailMessage("204 (No Content)"));
        return this;
    }

    // Statuses - 4$$

    public StatusCodeAssertions statusCodeIsBadRequest() {
        isNotNullResponse();

        checkStatusCode(HttpStatus.SC_BAD_REQUEST, getFailMessage("400 (Bad request)"));
        return this;
    }

    public StatusCodeAssertions statusCodeIsUnauthorized() {
        isNotNullResponse();

        checkStatusCode(HttpStatus.SC_UNAUTHORIZED, getFailMessage("401 (Unauthorized)"));
        return this;
    }

    public StatusCodeAssertions statusCodeIsForbidden() {
        isNotNullResponse();

        checkStatusCode(HttpStatus.SC_FORBIDDEN, getFailMessage("403 (Forbidden)"));
        return this;
    }

    public StatusCodeAssertions statusCodeIsNotFound() {
        isNotNullResponse();

        checkStatusCode(HttpStatus.SC_NOT_FOUND, getFailMessage("404 (Not found)"));
        return this;
    }

    public StatusCodeAssertions statusCodeIsConflict() {
        isNotNullResponse();

        checkStatusCode(HttpStatus.SC_CONFLICT, getFailMessage("409 (Conflict)"));
        return this;
    }

    public StatusCodeAssertions statusCodeIsIn(int... statusCodes) {
        isNotNullResponse();

        if (Arrays.stream(statusCodes).noneMatch(statusCode -> statusCode == getStatusCode())) {
            saveError(getFailMessage(String.format("was not in %s", Arrays.toString(statusCodes))), getStatusCode());
        }
        return this;
    }

    private void checkStatusCode(int expectedStatusCode, String failMessage) {
        int actualStatusCode = getStatusCode();

        if (actualStatusCode != expectedStatusCode) {
            saveError(failMessage, actualStatusCode);
        }
    }

    private void saveError(String failMessage, int actualStatusCode) {
        String errorMessage = failMessage + actualStatusCode
                + "\r\nResponse time was: " + actual.getTimeIn(TimeUnit.MILLISECONDS) + " ms"
                + "\r\nResponse status line: " + actual.getStatusLine()
                + "\r\nResponse headers was: \r\n" + actual.getHeaders().toString()
                + "\r\nResponse body was: \r\n" + actual.getBody().prettyPrint();
        saveErrorToAllureReport(errorMessage);
    }

    private void saveErrorToAllureReport(String errorMessage) {
        failWithMessage(errorMessage);
    }

    private String getFailMessage(String statusCode) {
        return String.format("HTTP Request failed! Expected status code %s and was ", statusCode);
    }

    private int getStatusCode() {
        return actual.then().extract().statusCode();
    }

    private void isNotNullResponse() {
        describedAs("HTTP Request").isNotNull();
    }

}