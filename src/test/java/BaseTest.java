import clients.UserClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import static org.hamcrest.Matchers.equalTo;

public abstract class BaseTest {
    protected UserClient userClient;

    @Test(groups = {"health", "e2e"})
    public void healthCheckTest() {
        RestAssured.get("/health-check").then().statusCode(200).body("message", equalTo("ok"));
    }

    @BeforeTest
    public void globalSetUp() {
        // Initialize UserClient instance
        userClient = UserClient.getInstance();
        log("Global setup complete.");
    }


    @BeforeMethod
    public void beforeMethodSetUp() {
        globalSetUp();
        log("Starting test: " + this.getClass().getSimpleName());
    }

    @AfterMethod
    public void tearDown() {
        // Common teardown after each test
        customTearDown();
        globalTearDown();
        log("Finished test: " + this.getClass().getSimpleName());
    }

    @AfterTest
    public void globalTearDown() {
        // Any global cleanup if necessary
        log("Global teardown complete.");
    }

    // Utility method for logging
    protected void log(String message) {
        System.out.println("[LOG]: " + message);
    }

    // Utility method for asserting status code
    protected void assertStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Expected status code: " + expectedStatusCode + " but got: " + actualStatusCode);
    }

    // Utility method for handling errors
    protected void handleError(String message) {
        log("ERROR: " + message);
        throw new RuntimeException(message);
    }

    // Placeholder method to allow custom setup by child classes
//    protected void customSetUp() {
//        // Can be overridden by child test classes
//    }

    protected void customTearDown() {
        // Default teardown implementation - can be overridden by child classes if needed
    }

}
