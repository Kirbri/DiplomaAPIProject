package api;

import models.auth.GenerateTokenLoginRequest;
import models.auth.TokenResponse;

import static io.restassured.RestAssured.given;
import static specs.GeneralSpec.requestSpecificationWithoutAuth;
import static specs.GeneralSpec.responseSpecification200;

public class CreateAuth {

    public TokenResponse successfulCreateAuth() {
        GenerateTokenLoginRequest requestData = new GenerateTokenLoginRequest();
        requestData.setUsername("admin");
        requestData.setPassword("password123");

        return given(requestSpecificationWithoutAuth)
                .body(requestData)
                .when()
                .post("/auth")
                .then()
                .spec(responseSpecification200)
                .extract().as(TokenResponse.class);
    }
}
