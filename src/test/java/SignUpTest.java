
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class SignUpTest extends BaseTest {

    private String uniqueEmail;
    private String password = "12345678";
    private String userId;


    @BeforeMethod
    public void setup() {
        super.beforeMethodSetUp();
        uniqueEmail = "user" + System.currentTimeMillis() + "@example.com";
    }

    @Test
    public void testUserSignup() {
        // Payload for signup request

        String requestBody = "{\n" +
                "\"email\":\"" + uniqueEmail + "\",\n" +
                "\"password\":\"" + password + "\"\n" +
                "}";

        // Send POST request to /signup endpoint
        Response response = given().contentType(ContentType.JSON).body(requestBody).when().post("/api/auth/signup").then().
                statusCode(201)
                .body("data.user.id", notNullValue()) // Update path for userId
                .body("data.user.email", equalTo(uniqueEmail)) // Update path for email
                .extract().response();

        // Store the userId for possible cleanup in teardown
        userId = response.jsonPath().getString("data.user.id");

    }

    @AfterMethod
    public void tearDown() {
        // Optional cleanup: Remove the created user if needed
        if (userId != null && !userId.isEmpty()) {
            Response deleteResponse = userClient.deleteUser(userId);
            if (deleteResponse.getStatusCode() != 200) {
                handleError("User deletion failed for userID: " + userId);
            }
            // In tearDown, update the check to expect a 204 status code for delete
            if (deleteResponse.getStatusCode() != 204) { // expect 204 for successful DELETE
                handleError("User deletion failed for userID: " + userId);
            }
        }
        super.tearDown();
    }



    @Override
    protected void customTearDown() {

    }
}
