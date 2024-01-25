package com.restassured.actions;

public class Registration {

    public static class Request{
        public static final String ROUTE = "/api/register";
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
        public static final String SCHEMA_PATH = "schema/authentication.json";
        public enum Body {
            ID("id"),
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
