package utils;

import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String token;
    protected RequestSpecification request;

    @BeforeClass
    public void setup() {
        // Get token before tests
        token = AuthUtils.getAccessToken("user1", "pass123");

        // Common request spec for APIs
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", token);
    }
}
