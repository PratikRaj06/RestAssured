package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.Product;
import utils.BaseTest;
import utils.JsonReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import utils.ExtentManager;
import java.util.List;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class AddProductTest extends BaseTest {

    ExtentReports extent = ExtentManager.getInstance();

    @Test
    public void addProductPositive() {
        ExtentTest test = extent.createTest("Add Product Positive");

        try {
            // Read test data
            Product product = JsonReader.readProduct("src/test/resources/testdata/addProduct.json", "positive");

            // Add product via POST
            Response response = given().spec(request)
                    .body(product)
                    .log().all()
                    .when()
                    .post("/OAuthRestApi/webapi/addProduct")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract()
                    .response();

            // Map response to List<Product>
            List<Product> products = response.jsonPath().getList("", Product.class);

            // Verify product exists in the response
            boolean found = products.stream()
                    .anyMatch(p -> p.getId() == product.getId() &&
                            p.getName().equals(product.getName()) &&
                            p.getAmount() == product.getAmount());

            // Assert using TestNG
            Assert.assertTrue(found, "Added product not found in response");

            test.pass("Product added successfully and verified in response");

        } catch (AssertionError e) {
            test.fail("Assertion failed: " + e.getMessage());
            throw e;
        } finally {
            extent.flush();
        }
    }

    @Test
    public void addProductNegative() {
        ExtentTest test = extent.createTest("Add Product Negative");

        try {
            Product product = JsonReader.readProduct("src/test/resources/testdata/addProduct.json", "negative");

            given().spec(request)
                    .body(product) // invalid data inside JSON
                    .log().all()
                    .when()
                    .post("/OAuthRestApi/webapi/addProduct")
                    .then()
                    .log().all()
                    .statusCode(400); // API must return 404 for invalid data

            test.pass("Status code 400 verified for invalid add");

        } catch (AssertionError e) {
            test.fail("Expected 400 but got different status: " + e.getMessage());
            throw e; // Let TestNG still mark it as failed
        } finally {
            extent.flush();
        }
    }
}
