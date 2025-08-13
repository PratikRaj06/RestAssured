package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AuthUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import utils.ExtentManager;

public class AuthTest {

    ExtentReports extent = ExtentManager.getInstance();

    @Test
    public void verifyAccessToken() {
        ExtentTest test = extent.createTest("Auth Test - Get Access Token");

        try {
            String token = AuthUtils.getAccessToken("user1", "pass123");

            Assert.assertNotNull(token, "Token should not be null");
            Assert.assertFalse(token.isEmpty(), "Token should not be empty");

            test.pass("Access token retrieved successfully: ");
        } catch (AssertionError e) {
            test.fail("Failed to get token: " + e.getMessage());
            throw e;
        } finally {
            extent.flush();
        }
    }
}
