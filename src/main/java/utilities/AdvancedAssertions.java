package utilities;

import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertNotNull;

public class AdvancedAssertions {

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(actualStatusCode, expectedStatusCode, "Assertion failed: Expected status code " + expectedStatusCode + " but got " + actualStatusCode + ". Response body: " + response.getBody().asString());
    }

    // Asserts that the response contains a key-value pair in the payload
    public static void assertPayloadContains(Response response, String key, Object expectedValue) {
        Object actualValue = response.jsonPath().get(key);
        Assert.assertNotNull(actualValue, String.format("Key '%s' not found in response payload. Response body: %s", key, response.getBody().asString()));
        assertEquals(actualValue, expectedValue, String.format("Expected value for key '%s': %s, but got: %s. Response body: %s", key, expectedValue, actualValue, response.getBody().asString()));
    }

    // Asserts that the response headers contain a specific key-value pair
    public static void assertHeaderContains(Response response, String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        assertNotNull(actualValue, "Assertion failed: Header [" + headerName + "] is missing. Response body: " + response.getBody().asString());
        assertEquals(actualValue, expectedValue, "Assertion failed: Expected header [" + headerName + "] value " + expectedValue + " but got " + actualValue + ". Response body: " + response.getBody().asString());
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
        Assert.assertNotNull(token, String.format("Expected token at path '%s', but none found. Response body: %s", tokenPath, response.getBody().asString()));
    }

    // Asserts that a JSON path exists and matches the expected value
    public static void assertJsonPathValue(Response response, String jsonPath, Object expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPath);
        assertNotNull((String) actualValue, "Assertion failed: JSON path [" + jsonPath + "] does not exist in the response. Response body: " + response.getBody().asString());
        assertEquals(actualValue, expectedValue, "Assertion failed: Expected JSON path [" + jsonPath + "] value " + expectedValue + " but got " + actualValue + ". Response body: " + response.getBody().asString());
    }

    // Asserts that a response body contains a specific value
    public static void assertResponseBodyContains(Response response, String expectedValue) {
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains(expectedValue), "Assertion failed: Response body does not contain expected value [" + expectedValue + "]. Response body: " + responseBody);
    }

    // Utility method for logging errors and throwing exceptions
    public static void handleError(String message) {
        // Log the error message
        System.err.println("[ERROR]: " + message);
        // Throw a runtime exception with the error message
        throw new RuntimeException(message);
    }

    public static void assertJsonField(Response response, String field, Object expectedValue) {
        Object actualValue = response.jsonPath().get(field);
        Assert.assertEquals(actualValue, expectedValue,
                "Expected value for field '" + field + "' is " + expectedValue + " but found " + actualValue);
    }
}
