package io.swagger.pet.store.tests.chapter2.xml.path;

import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class XmlPathTest {

    @Test
    public void xmlPathExample() {
        Response response = given()
                .log()
                .all()
                .filter(new ResponseLoggingFilter())
                .contentType("application/xml")
                .accept("application/xml")
                .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<Pet>\n" +
                        "\t<id>123</id>\n" +
                        "\t<Category>\n" +
                        "\t\t<id>0</id>\n" +
                        "\t\t<name>string</name>\n" +
                        "\t</Category>\n" +
                        "\t<name>doggie</name>\n" +
                        "\t<photoUrl>\n" +
                        "\t\t<photoUrl>string</photoUrl>\n" +
                        "\t</photoUrl>\n" +
                        "\t<tag>\n" +
                        "\t\t<Tag>\n" +
                        "\t\t\t<id>0</id>\n" +
                        "\t\t\t<name>string</name>\n" +
                        "\t\t</Tag>\n" +
                        "\t</tag>\n" +
                        "\t<status>available</status>\n" +
                        "</Pet>")
                .post("https://petstore.swagger.io/v2/pet")
                .then().assertThat().statusCode(200)
                .extract().response();

        long petId = response.xmlPath().getLong("pet.id");

        given()
                .log()
                .all()
                .filter(new ResponseLoggingFilter())
                .contentType("application/json")
                .accept("application/json")
                .pathParam("petId", petId)
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then().assertThat().statusCode(200);
    }


}
