package tests;

import org.testng.annotations.Test;
import utils.BaseTest;
import utils.JsonReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import utils.ExtentManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetProductTest extends BaseTest {

    ExtentReports extent = ExtentManager.getInstance();

    @Test
    public void getAllProducts() {
        ExtentTest test = extent.createTest("Get All Products");

        try {
            given().spec(request)
                    .log().all()
                    .when()
                    .get("/OAuthRestApi/webapi/getAllProducts")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("size()", greaterThan(0)) // ensure not empty
                    .body("id", everyItem(notNullValue()))
                    .body("name", everyItem(not(is(emptyOrNullString()))))
                    .body("amount", everyItem(notNullValue()));

            test.pass("All products have valid structure and data");
        } catch (AssertionError e) {
            test.fail("Assertion failed: " + e.getMessage());
            throw e;
        } finally {
            extent.flush();
        }
    }

    @Test
    public void getProductByIdPositive() {
        ExtentTest test = extent.createTest("Get Product By ID Positive");

        try {
            int id = JsonReader.readId("src/test/resources/testdata/getProductById.json", "positive");

            given().spec(request)
                    .log().all()
                    .when()
                    .get("/OAuthRestApi/webapi/getProductbyId/" + id)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("id", equalTo(String.valueOf(id))); // response IDs are strings

            test.pass("Product by ID matches expected data");
        } catch (AssertionError e) {
            test.fail("Assertion failed: " + e.getMessage());
            throw e;
        } finally {
            extent.flush();
        }
    }

    @Test
    public void getProductByIdNegative() {
        ExtentTest test = extent.createTest("Get Product By ID Negative");

        try {
            int id = JsonReader.readId("src/test/resources/testdata/getProductById.json", "negative");

            given().spec(request)
                    .log().all()
                    .when()
                    .get("/OAuthRestApi/webapi/getProductbyId/" + id)
                    .then()
                    .log().all()
                    .statusCode(404);

            test.pass("Status code 404 verified for invalid product ID");
        } catch (AssertionError e) {
            test.fail("Expected 404 but got different status: " + e.getMessage());
            throw e;
        } finally {
            extent.flush();
        }
    }

    @Test
    public void getProductByNamePositive() {
        ExtentTest test = extent.createTest("Get Product By Name Positive");

        try {
            String name = JsonReader.readName("src/test/resources/testdata/getProductByName.json", "positive");

            given().spec(request)
                    .log().all()
                    .when()
                    .get("/OAuthRestApi/webapi/viewProductByName?name=" + name)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("name", equalTo(name));

            test.pass("Product by Name matches expected data");
        } catch (AssertionError e) {
            test.fail("Assertion failed: " + e.getMessage());
            throw e;
        } finally {
            extent.flush();
        }
    }

    @Test
    public void getProductByNameNegative() {
        ExtentTest test = extent.createTest("Get Product By Name Negative");

        try {
            String name = JsonReader.readName("src/test/resources/testdata/getProductByName.json", "negative");

            given().spec(request)
                    .log().all()
                    .when()
                    .get("/OAuthRestApi/webapi/viewProductByName?name=" + name)
                    .then()
                    .log().all()
                    .statusCode(404);

            test.pass("Status code 404 verified for invalid product name");
        } catch (AssertionError e) {
            test.fail("Expected 404 but got different status: " + e.getMessage());
            throw e;
        } finally {
            extent.flush();
        }
    }
}
