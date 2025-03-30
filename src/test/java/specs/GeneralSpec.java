package specs;

import data.AuthData;
import helpers.CustomAllureListener;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class GeneralSpec {
    private static final String authorization = new AuthData().authorization;

    public static final RequestSpecification requestSpecificationWithBasicAuth = with()
            .filter(CustomAllureListener.withCustomTemplates())
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("Authorization", authorization)
            .log().all()
            .contentType(JSON);

    public static final RequestSpecification requestSpecificationWithoutAuth = with()
            .filter(CustomAllureListener.withCustomTemplates())
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .log().all()
            .contentType(JSON);

    public static final ResponseSpecification responseSpecification200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();

    public static final ResponseSpecification responseSpecification200WithExpectBody = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .expectBody("body", is("Cannot read properties of null (reading 'checkin')"))
            .build();

    public static final ResponseSpecification responseSpecification201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .build();

    public static final ResponseSpecification responseSpecification403 = new ResponseSpecBuilder()
            .expectStatusCode(403)
            .log(ALL)
            .build();

    public static final ResponseSpecification responseSpecification404 = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(ALL)
            .build();

    public static final ResponseSpecification responseSpecification405 = new ResponseSpecBuilder()
            .expectStatusCode(405)
            .log(ALL)
            .build();

    public static final ResponseSpecification responseSpecification500 = new ResponseSpecBuilder()
            .expectStatusCode(500)
            .log(ALL)
            .build();
}