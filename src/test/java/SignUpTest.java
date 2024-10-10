import io.restassured.response.Response;
import models.UserSignupResponse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.AdvancedAssertions;

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
        Response response = userClient.createUser(uniqueEmail, password);
        System.out.println(response.jsonPath().getString("data"));

        // Deserialize response into UserSignupResponse POJO
        UserSignupResponse signupResponse = response.as(UserSignupResponse.class);
        // Using advanced assertion methods
        AdvancedAssertions.assertStatusCode(response, 201); // Check status code
        AdvancedAssertions.assertPayloadContains(response, "data.user.email", uniqueEmail); // Check email in response
        userId = response.jsonPath().getString("data.user.id");
        System.out.println("userId :" + userId);
        AdvancedAssertions.assertPayloadContains(response, "data.user.id", userId); // Check if userId is present
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
