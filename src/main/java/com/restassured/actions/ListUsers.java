package com.restassured.actions;

public class ListUsers {

    public static class Request {
        public static final String ROUTE = "/api/users";
        public static final String PER_PAGE = "per_page";
    }

    public static class Response {
        public static final int SUCCESS_CODE = 200;
        public static final String SCHEMA_PATH = "schema/list_users.json";
    }
}