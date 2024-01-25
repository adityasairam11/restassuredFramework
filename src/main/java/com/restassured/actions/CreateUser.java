package com.restassured.actions;

public class CreateUser {

    public static class Request {
        public static final String ROUTE = "/users";
        public enum Parameter {
            NAME("name"),
            JOB("job");

            private final String queryParam;

            Parameter(String queryParam) { this.queryParam = queryParam; }
            public String key(){ return queryParam; }
        }
    }

    public static class Response {
        public static final int SUCCESS_CODE = 201;
        public static final String SCHEMA_PATH = "schema/create_users.json";
        public enum Parameter {
            NAME("name"),
            JOB("job"),
            ID("id"),
            CREATED_AT("created_at");

            private final String queryParam;

            Parameter(String queryParam) { this.queryParam = queryParam; }
            public String key(){ return queryParam; }
        }
    }
}
