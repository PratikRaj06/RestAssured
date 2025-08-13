package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AuthUtils {

    private static final String BASE_URI = "https://webapps.tekstac.com/";

    public static String getAccessToken(String username, String password) {
        RestAssured.baseURI = BASE_URI;

        // Step 1: Get auth code
        Response authResponse = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", username)
                .formParam("password", password)
                .post("/OAuthRestApi/webapi/auth/login");

        String authCode = authResponse.jsonPath().getString("auth_code");

        // Step 2: Exchange auth code for access token
        Response tokenResponse = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("auth_code", authCode)
                .post("/OAuthRestApi/webapi/auth/token");

        return tokenResponse.jsonPath().getString("access_token");
    }
}
