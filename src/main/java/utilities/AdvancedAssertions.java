package utilities;

import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;

public class AdvancedAssertions {

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        String.format("Expected status code: %d, but got: %d. Response body: %s", expectedStatusCode, actualStatusCode, response.getBody().asString());
    }

    // Asserts that the response contains a key-value pair in the payload
    public static void assertPayloadContains(Response response, String key, Object expectedValue) {
        Object actualValue = response.jsonPath().get(key);
        Assert.assertNotNull(actualValue,
                String.format("Key '%s' not found in response payload. Response body: %s", key, response.getBody().asString()));
        Assert.assertEquals(actualValue, expectedValue,
                String.format("Expected value for key '%s': %s, but got: %s. Response body: %s",
                        key, expectedValue, actualValue, response.getBody().asString()));
    }

    // Asserts that the response headers contain a specific key-value pair
    public static void assertHeaderContains(Response response, String header, String expectedValue) {
        String actualValue = response.getHeader(header);
        Assert.assertNotNull(actualValue,
                String.format("Header '%s' not found in response. Response headers: %s", header, response.getHeaders().toString()));
        Assert.assertEquals(actualValue, expectedValue,
                String.format("Expected value for header '%s': %s, but got: %s", header, expectedValue, actualValue));
    }

    // Asserts multiple payload key-value pairs at once
    public static void assertPayloadContainsMultiple(Response response, Map<String, Object> expectedValues) {
        for (Map.Entry<String, Object> entry : expectedValues.entrySet()) {
            assertPayloadContains(response, entry.getKey(), entry.getValue());
        }
    }

    // Asserts that the response payload contains a non-null token (for login scenarios)
    public static void assertTokenPresent(Response response, String tokenPath) {
        String token = response.jsonPath().getString(tokenPath);
        Assert.assertNotNull(token,
                String.format("Expected token at path '%s', but none found. Response body: %s", tokenPath, response.getBody().asString()));
    }
}
