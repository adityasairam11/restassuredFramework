package com.restassured.records.Listusers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.ANY)
public record User(


        @SerializedName("page")
        @Expose int page,

        @SerializedName("per_page")
        @Expose int perPage,

        @SerializedName("total")
        @Expose int total,

        @SerializedName("total_pages")
        @Expose int totalPages,

        List<Data> data,
        Support support
) {
}