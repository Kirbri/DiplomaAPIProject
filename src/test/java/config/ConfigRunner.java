package config;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.aeonbits.owner.ConfigFactory;

public class ConfigRunner {
    private final CredentialsConfig CONFIG_CREDENTIALS;

    public ConfigRunner() {
        CONFIG_CREDENTIALS = ConfigFactory.create(CredentialsConfig.class, System.getProperties());

        setupConfiguration();
    }

    private void setupConfiguration() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.defaultParser = Parser.JSON;
    }
}