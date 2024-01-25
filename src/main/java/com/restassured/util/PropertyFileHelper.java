package com.restassured.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileHelper {
    static Properties prop = new Properties();
    FileInputStream fis = null;

    public void readPropFile(String env) {
        try {
            fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config/" + env + ".properties");
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getGlobalValue(String value) {
        return prop.getProperty(value);
    }
}

