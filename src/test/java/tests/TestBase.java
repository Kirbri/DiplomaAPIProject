package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class TestBase {

    @BeforeAll
    public static void setUp() {
        Configuration.baseUrl = "https://restful-booker.herokuapp.com";
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.defaultParser = Parser.JSON;
        Configuration.timeout = 10000;
        Configuration.remote = "https://" + System.getProperty("loginRemote") + "@"
                + System.getProperty("url") + "/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }
}