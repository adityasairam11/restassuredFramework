package com.restassured.util;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

public class SchemaParser {

    String schemaPath;

    public SchemaParser(String schemaPath){
        this.schemaPath = schemaPath;
    }

    public Set getKeys() throws IOException {
        String jsonSchemaFile = FileUtils.readFileToString(new File(
                Objects.requireNonNull(this.getClass().getClassLoader().getResource(schemaPath)).getFile()));
        ReadContext readContext = JsonPath.parse(jsonSchemaFile);
        LinkedHashMap page = JsonPath.read(jsonSchemaFile, "$.properties");
        return page.keySet();
    }

}