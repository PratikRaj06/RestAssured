package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.Product;
import utils.BaseTest;
import utils.JsonReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import utils.ExtentManager;
import io.restassured.response.Response;
import java.util.List;
import static io.restassured.RestAssured.given;

public class UpdateProductTest extends BaseTest {

    ExtentReports extent = ExtentManager.getInstance();

    @Test
    public void updateProductPositive() {
        ExtentTest test = extent.createTest("Update Product Positive");

        try {
            // Read positive test data from JSON
            Product product = JsonReader.readProduct("src/test/resources/testdata/updateProduct.json", "positive");

            // Send PUT request to update product
            Response response = given().spec(request)
                    .body(product)
                    .log().all()
                    .when()
                    .put("/OAuthRestApi/webapi/updateProduct/" + product.getId())
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract()
                    .response();

            // Map response to List<Product>
            List<Product> products = response.jsonPath().getList("", Product.class);

            // Verify updated product exists
            boolean found = products.stream()
                    .anyMatch(p -> p.getId() == product.getId() &&
                            p.getName().equals(product.getName()) &&
                            p.getAmount() == product.getAmount());

            Assert.assertTrue(found, "Updated product not found in response");

            test.pass("Product updated successfully and verified in response");

        } catch (AssertionError e) {
            test.fail("Assertion failed: " + e.getMessage());
            throw e;
        } finally {
            extent.flush();
        }
    }

    @Test
    public void updateProductNegative() {
        ExtentTest test = extent.createTest("Update Product Negative");

        try {
            Product product = JsonReader.readProduct("src/test/resources/testdata/updateProduct.json", "negative");

            given().spec(request)
                    .body(product)
                    .log().all()
                    .when()
                    .put("/OAuthRestApi/webapi/updateProduct/" + product.getId())
                    .then()
                    .log().all()
                    .statusCode(404); // Expecting 404 for invalid update

            test.pass("Status code 404 verified for invalid update");

        } catch (AssertionError e) {
            test.fail("Expected 404 but got different status: " + e.getMessage());
            throw e; // Let TestNG still mark it as failed
        } finally {
            extent.flush();
        }
    }
}
