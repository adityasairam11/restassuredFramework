package com.restassured;

import com.restassured.actions.DeleteUser;
import com.restassured.util.APIUtils;
import com.restassured.util.PropertyFileHelper;

import com.restassured.actions.Authentication;
import com.restassured.actions.Registration;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static com.restassured.data.TestTags.SMOKE;

public class AuthenticationTest {

    APIUtils apiUtil = new APIUtils();
    static PropertyFileHelper propHelper = new PropertyFileHelper();
    Map<String, String> headerParams;
    HashMap<String, Object> requestParams;

    String email;
    String password;
    String id;

    @BeforeAll
    public static void setEnvironment() {
        propHelper.readPropFile("QA");
    }

    @BeforeEach
    @DisplayName("Creating a new user")
    @Description("A pre-step to register a user before login attempt")
    public void registerUser() {
        requestParams = new HashMap<>();
        headerParams = new HashMap<>();
        email = propHelper.getGlobalValue("reqres.email");
        password = propHelper.getGlobalValue("reqres.password");
        requestParams.put(Registration.Request.Body.EMAIL.property(), email);
        requestParams.put(Registration.Request.Body.PASSWORD.property(), password);

        RequestSpecification requestSpecification = apiUtil.requestBuilder("reqres.baseUrl", "JSON", headerParams, null);
        Response response = apiUtil.responseBuilder(requestSpecification, "POST", Registration.Request.ROUTE, requestParams, 200);
        id = apiUtil.getJsonPath(response, Registration.Response.Body.ID.property());
        Allure.step("A pre-step to register a user before login attempt");
    }


    @AfterEach
    @DisplayName("Deleting the user which was created via the above registerUser method")
    @Description("A post-step to delete the registered user after the login test")
    public void deleteUser() {
        headerParams  = new HashMap<>();
        RequestSpecification requestSpecification = apiUtil.requestBuilder("reqres.baseUrl", "JSON", headerParams, null);
        apiUtil.responseBuilder(requestSpecification, "DELETE", DeleteUser.Request.ROUTE + id, null, DeleteUser.Response.SUCCESS_CODE);
        Allure.step("A post-step to delete the registered user after the login test");
    }

    @Test
    @Tag(SMOKE)
    @DisplayName("Should allow valid user to login")
    @Description("Test if a user with valid credentials is able to login.")
    public void shouldLoginValidUser() {
        headerParams  = new HashMap<>();
        requestParams.put(Authentication.Request.Body.EMAIL.property(), email);
        requestParams.put(Authentication.Request.Body.PASSWORD.property(), password);
        headerParams.put("User", "QA");
        Allure.step("User builds the request payload and makes a call to the API");
        RequestSpecification requestSpecification = apiUtil.requestBuilder("reqres.baseUrl", "JSON", headerParams, null);
        apiUtil.responseBuilder(requestSpecification, "POST", Authentication.Request.ROUTE, requestParams, Authentication.Response.SUCCESS_CODE);
        Allure.step("User validates the API response when valid login attempt happens");
    }

    @Test
    @Tag(SMOKE)
    @DisplayName("Should not allow invalid user to login")
    @Description("Test if a user with invalid credentials is unable to login.")
    public void loginFailInvalidUser() {
        headerParams  = new HashMap<>();
        requestParams = new HashMap<>();
        requestParams.put(Authentication.Request.Body.EMAIL.property(), "dummy");
        requestParams.put(Authentication.Request.Body.PASSWORD.property(), "dummy");
        Allure.step("User builds the request payload and makes a call to the API");
        RequestSpecification requestSpecification = apiUtil.requestBuilder("reqres.baseUrl", "JSON", headerParams, null);
        apiUtil.responseBuilder(requestSpecification, "POST", Authentication.Request.ROUTE, requestParams, Authentication.Response.FAILURE_CODE);
        Allure.step("User validates the API response when invalid login attempt happens");
    }
}
