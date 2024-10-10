package deserializer;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.util.Map;
import java.util.stream.Collectors;

public class ApiResponseDeserializer<T> {
    private ObjectMapper objectMapper;
    private final Class<T> responseClass;

    // Constructor that accepts the class type for deserialization
    public ApiResponseDeserializer(Class<T> responseClass) {
        this.responseClass = responseClass;
        this.objectMapper = new ObjectMapper();
    }

    // Method to handle the complete deserialization process
    public T deserializeResponse(Response response) {
        try {

            T responseBody = objectMapper.readValue(response.getBody().asString(), responseClass);

            // You can add custom logic to deserialize headers and status codes if required
            int statusCode = response.getStatusCode();
            Map<String, String> headers = response.getHeaders().asList().stream()
                    .collect(Collectors.toMap(h -> h.getName(), h -> h.getValue()));

            // Check for response structure validation if needed (status code, headers)
            validateResponse(statusCode, headers);

            return responseBody;


        } catch (Exception e) {
            // Handle errors with informative messages
            throw new RuntimeException("Failed to deserialize the API response for class: " +
                    responseClass.getSimpleName() + ". Error: " + e.getMessage() +
                    ". Response Body: " + response.getBody().asString(), e);
        }


    }

    // Validation method for status codes and headers
    private void validateResponse(int statusCode, Map<String, String> headers) {
        if (statusCode < 200 || statusCode >= 300) {
            throw new RuntimeException("Unexpected status code: " + statusCode + ". Expected 2xx success codes.");
        }
        if (!headers.containsKey("Content-Type") || !headers.get("Content-Type").contains("application/json")) {
            throw new RuntimeException("Missing or incorrect 'Content-Type' header. Expected 'application/json'.");
        }
    }

}