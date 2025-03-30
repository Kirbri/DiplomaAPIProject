package tests;

import config.ConfigRunner;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    public static void setUp() {
        new ConfigRunner();
    }
}