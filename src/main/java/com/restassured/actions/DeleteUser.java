package com.restassured.actions;

public class DeleteUser {

    public static class Request {
        public static final String ROUTE = "/api/users";
    }

    public static class Response {
        public static final int SUCCESS_CODE = 204;
        public static final String SCHEMA_PATH = "schema/list_users.json";
    }

}
