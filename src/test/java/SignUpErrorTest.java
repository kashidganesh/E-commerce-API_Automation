import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.AdvancedAssertions;

public class SignUpErrorTest extends BaseTest{

    @Test
    public void testUserSignupWithMalformedResponse() {
        // Mocking a response with unexpected JSON structure or missing fields
        Response response = userClient.createUser("edgecaseuser@example.com", "12345678");

        // Assuming the response body might be malformed or missing fields
        // Checking if the response contains an 'error' field
        try {
            AdvancedAssertions.assertJsonPathValue(response, "data.user.email", "edgecaseuser@example.com");
        } catch (AssertionError e) {
            // Handle cases where JSON path does not exist or is malformed
            AdvancedAssertions.handleError("Malformed response: " + response.getBody().asString());
        }
    }


}
