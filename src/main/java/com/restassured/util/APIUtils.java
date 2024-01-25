package com.restassured.util;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class APIUtils {

    static PropertyFileHelper propertyFileHelper = new PropertyFileHelper();
    static RequestSpecBuilder requestSpecBuilder;
    ValidatableResponse validatableResponse;

    public RequestSpecification requestBuilder(String hostName, String apiContentType, Map<String, String> headerParams, Map<String, Object> queryParams) {

        requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri(propertyFileHelper.getGlobalValue(hostName))
                .setContentType(ContentType.valueOf(apiContentType.toUpperCase()));

        if (queryParams != null) {
            requestSpecBuilder = requestSpecBuilder.addQueryParams(queryParams);
        }
        if (headerParams != null) {
            requestSpecBuilder = requestSpecBuilder.addHeaders(headerParams);
        }
        return requestSpecBuilder.build().log().all()
                .filter(new AllureRestAssured());
    }

    public Response responseBuilder(RequestSpecification requestSpecification, String HTTPMethod, String resourcePath, HashMap<String, Object> payload, Integer expectedStatusCode) {

        requestSpecification = given().spec(requestSpecification).when();

        switch (HTTPMethod.toUpperCase()) {
            case "GET":
                validatableResponse = requestSpecification
                        .get(resourcePath).then();
                break;

            case "POST":
                validatableResponse = requestSpecification
                        .body(payload)
                        .post(resourcePath).then();
                break;

            case "PUT":
                validatableResponse = requestSpecification
                        .body(payload)
                        .put(resourcePath).then();
                break;

            case "PATCH":
                validatableResponse = requestSpecification
                        .body(payload)
                        .patch(resourcePath).then();
                break;

            case "DELETE":
                validatableResponse = requestSpecification
                        .delete(resourcePath).then();
                break;

            default:
                throw new RuntimeException("Invalid HTTP action has been passed");
        }

        return validatableResponse.log().all()
                .assertThat()
                .statusCode(expectedStatusCode).extract().response();

    }

    public void validateJsonSchema(String schemaPath) {
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    public String getJsonPath(Response response, String key) {
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }
}
