package tests;

import org.testng.annotations.Test;
import utils.BaseTest;
import utils.JsonReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import utils.ExtentManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;

public class DeleteProductTest extends BaseTest {

    ExtentReports extent = ExtentManager.getInstance();

    @Test
    public void deleteProductPositive() {
        ExtentTest test = extent.createTest("Delete Product Positive");

        try {
            int id = JsonReader.readId("src/test/resources/testdata/deleteProductById.json", "positive");

            // Perform deletion and verify status code 200
            given().spec(request)
                    .log().all()
                    .when()
                    .delete("/OAuthRestApi/webapi/delProduct/" + id)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("id", not(hasItem(String.valueOf(id)))); // Verify deleted product ID is not present

            test.pass("Status code 200 verified and deleted product not present in response");

        } catch (AssertionError e) {
            test.fail("Assertion failed: " + e.getMessage());
            throw e; // Let TestNG still mark it as failed
        } finally {
            extent.flush();
        }
    }

    @Test
    public void deleteProductNegative() {
        ExtentTest test = extent.createTest("Delete Product Negative");

        try {
            int id = JsonReader.readId("src/test/resources/testdata/deleteProductById.json", "negative");

            given().spec(request)
                    .log().all()
                    .when()
                    .delete("/OAuthRestApi/webapi/delProduct/" + id)
                    .then()
                    .log().all()
                    .statusCode(404); // Expecting 404 for non-existent product

            test.pass("Status code 404 verified for non-existent product");

        } catch (AssertionError e) {
            test.fail("Expected 404 but got different status: " + e.getMessage());
            throw e; // Let TestNG still mark it as failed
        } finally {
            extent.flush();
        }
    }
}
