package com.restassured.records.Listusers;

public record Data(
        int id,
        String email,
        String first_name,
        String last_name,
        String avatar
) {
}
