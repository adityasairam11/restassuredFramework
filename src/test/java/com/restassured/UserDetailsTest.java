package com.restassured;

import com.restassured.actions.ListUsers;

import com.restassured.records.Listusers.User;
import com.restassured.util.APIUtils;
import com.restassured.util.PropertyFileHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static com.restassured.data.TestTags.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;

public class UserDetailsTest {
    static PropertyFileHelper propHelper = new PropertyFileHelper();
    APIUtils apiUtil = new APIUtils();

    Map<String, Object> queryParams;
    Map<String, String> headerParams;

    @BeforeAll
    public static void setEnvironment() {
        propHelper.readPropFile("QA");
    }

    @Test
    @Tag(HEALTHCHECK)
    @DisplayName("Should be able to hit the users endpoint")
    @Description("Test if the user endpoint can be accessed successfully and validate the JSON schema.")
    public void healthCheck() {
        queryParams = new HashMap<>();
        headerParams = new HashMap<>();
        RequestSpecification requestSpecification = apiUtil.requestBuilder("reqres.baseUrl", "json", null, null);
        apiUtil.responseBuilder(requestSpecification, "GET", ListUsers.Request.ROUTE, null, ListUsers.Response.SUCCESS_CODE);
        Allure.step("User validates the API response and asserts the response code");
        apiUtil.validateJsonSchema(ListUsers.Response.SCHEMA_PATH);
        Allure.step("User validates the JSON schema");
    }

    @Test
    @Tag(SMOKE)
    @DisplayName("Should list all users by pagination")
    @Description("Test if the user end point with pagination can be hit and the response is asserted successfully.")
    public void shouldListAllUsersByPagination() {
        queryParams = new HashMap<>();
        headerParams = new HashMap<>();
        queryParams.put(ListUsers.Request.PER_PAGE, 5);
        headerParams.put("User", "QA");

        RequestSpecification requestSpecification = apiUtil.requestBuilder("reqres.baseUrl", "JSON", headerParams, queryParams);
        Response response = apiUtil.responseBuilder(requestSpecification, "GET", ListUsers.Request.ROUTE, null, ListUsers.Response.SUCCESS_CODE);
        Allure.step("User validates the API response which displays list of all user by as per pagination");
        var user = response.as(User.class, ObjectMapperType.GSON);

        assertThat(user.page(), is(1));
        assertThat(user.perPage(), is(5));
        assertThat(user.total(), is(12));
        assertThat(user.totalPages(), is(3));
        assertThat(user.data(), is(not(empty())));
        Allure.step("All the API response assertions have been successful");
    }
}