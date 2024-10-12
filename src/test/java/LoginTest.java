import deserializer.ApiResponseWrapper;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojos.LoginResponseModel;
import utilities.AdvancedAssertions;

import static org.testng.Assert.assertEquals;

public class LoginTest extends BaseTest {
    private String uniqueEmail;
    private String password = "12345678";
    private String userId;

    @BeforeMethod
    public void setUp() {
        super.beforeMethodSetUp();
        uniqueEmail = "user" + System.currentTimeMillis() + "@example.com";
        Response signupResponse = userClient.createUser(uniqueEmail, password);
        AdvancedAssertions.assertStatusCode(signupResponse, 201);
        userId = signupResponse.jsonPath().getString("user.id");
        System.out.println("userId :" +userId);
    }

    @Test
    public void testUserLogin() {
       /* Response loginResponse = userClient.authenticateUser(uniqueEmail, password);
        AdvancedAssertions.assertStatusCode(loginResponse, 200);
        AdvancedAssertions.assertTokenPresent(loginResponse, "data.session.access_token");
        // Assert multiple payload values (if needed)
        AdvancedAssertions.assertPayloadContains(loginResponse, "data.user.email", uniqueEmail);*/

        ApiResponseWrapper<LoginResponseModel> responseWrapper = userClient.authenticateUser(uniqueEmail, password);
        LoginResponseModel responseBody = responseWrapper.getResponseBody();

        assertEquals(responseWrapper.getStatusCode(), 200, "Expected status code to be 200");
        assertEquals(responseWrapper.getHeaders().get("Content-Type"), "application/json", "Expected content type to be 'application/json'");
    }

    @AfterMethod
    public void tearDown() {
        if (userId != null) {
            Response deleteResponse = userClient.deleteUser(userId);
            //AdvancedAssertions.assertStatusCode(deleteResponse, 204);
        }
        super.tearDown();
    }

    @Override
    protected void customTearDown() {

    }
}



