package api;

import data.AuthData;
import models.auth.GenerateTokenLoginRequest;
import models.auth.TokenResponse;

import static io.restassured.RestAssured.given;
import static specs.GeneralSpec.requestSpecificationWithoutAuth;
import static specs.GeneralSpec.responseSpecification200;

public class CreateAuth {
    AuthData authData = new AuthData();

    public TokenResponse successfulCreateAuth() {
        GenerateTokenLoginRequest requestData = new GenerateTokenLoginRequest(authData.username, authData.password);

        return given(requestSpecificationWithoutAuth)
                .body(requestData)
                .when()
                .post("/auth")
                .then()
                .spec(responseSpecification200)
                .extract().as(TokenResponse.class);
    }
}