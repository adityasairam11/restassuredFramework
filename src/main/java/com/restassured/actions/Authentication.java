package com.restassured.actions;

public class Authentication {

    public static class Request {
        public static final String ROUTE = "/api/login";

        public enum Body {
            EMAIL("email"),
            PASSWORD("password");

            private final String property;

            Body(String property) {
                this.property = property;
            }

            public String property() {
                return property;
            }
        }
    }

    public static class Response {
        public static final int SUCCESS_CODE = 200;
        public static final int FAILURE_CODE = 400;
        public static final String SCHEMA_PATH = "schema/authentication.json";
        public static final String ERROR_MESSAGE = "Missing email or username";
        public enum Body {
            TOKEN("token");

            private final String property;

            Body(String property) {
                this.property = property;
            }

            public String property() {
                return property;
            }
        }
    }
}
