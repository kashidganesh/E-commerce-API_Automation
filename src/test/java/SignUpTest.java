import deserializer.ApiResponseWrapper;
import io.restassured.response.Response;
import models.UserSignupResponse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojos.SignupResponseModel;
import utilities.AdvancedAssertions;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

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
        ApiResponseWrapper<SignupResponseModel> responseWrapper  = userClient.createUser(uniqueEmail, password);
        SignupResponseModel signupResponse = responseWrapper.getResponseBody();

       /* // Using advanced assertion methods
        AdvancedAssertions.assertStatusCode(response, 201); // Check status code
        AdvancedAssertions.assertPayloadContains(response, "data.user.email", uniqueEmail); // Check email in response

        AdvancedAssertions.assertPayloadContains(response, "data.user.id", userId); // Check if userId is present*/

         /*Assert the status code*/
       // assertEquals(responseWrapper.getStatusCode(), 200, "Expected status code to be 200");
        assertEquals(responseWrapper.getStatusCode(), 201, "Expected status code to be 201");

        // Assert the headers
        assertTrue(responseWrapper.getHeaders().containsKey("Content-Type"));
        assertEquals(responseWrapper.getHeaders().get("Content-Type"), "application/json");

        // Assert the response body fields
        assertNotNull(signupResponse.getUserId(), "User ID should not be null");
        assertNotNull(signupResponse.getToken(), "Token should not be null");

        // Assert body
      /*  SignupResponseModel responseBody = responseWrapper.getBody();
        assertEquals(responseBody.getEmail(), uniqueEmail, "Expected email to match the one provided in the request");
*/
    }

    @AfterMethod
    public void tearDown() {
        if (userId != null) {
            Response deleteResponse = userClient.deleteUser(userId);
            // AdvancedAssertions.assertStatusCode(deleteResponse, 204);
        }
        super.tearDown();
    }


    @Override
    protected void customTearDown() {

    }
}
