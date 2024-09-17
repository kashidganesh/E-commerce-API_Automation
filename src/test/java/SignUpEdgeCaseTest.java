import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.AdvancedAssertions;

public class SignUpEdgeCaseTest extends BaseTest {

    @Test
    public void testUserSignupWithExistingEmail() {
        // Create a user with an email
        String existingEmail = "user1726577548005@example.com";
        String password = "12345678";
        userClient.createUser(existingEmail, password);

        // Attempt to create another user with the same email
        Response response = userClient.createUser(existingEmail, password);

        // Expecting a 400 Bad Request status code due to duplicate email
        AdvancedAssertions.assertStatusCode(response, 401);
        // Assuming the response contains a specific error message for duplicate email
        AdvancedAssertions.assertJsonPathValue(response, "error", "User already registered");
    }

}
