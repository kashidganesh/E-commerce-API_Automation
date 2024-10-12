package deserializer;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.response.Response;
import pojos.LoginResponseModel;

import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ApiResponseDeserializer<T> {
    private ObjectMapper objectMapper;
    private final Class<T> responseType;

    // Constructor that accepts the class type for deserialization
    public ApiResponseDeserializer(Class<T> responseClass) {
        this.responseType = responseClass;
        this.objectMapper = new ObjectMapper();
    }

    public static ApiResponseWrapper deserializeResponse(Response response, Class<LoginResponseModel> loginResponseModelClass) {

        return null;
    }

    // Method to handle the complete deserialization process
    public ApiResponseWrapper<T> deserializeResponse(Response response) {
        try {

            int statusCode = response.getStatusCode();
            Map<String, String> headers = response.getHeaders().asList().stream()
                    .collect(Collectors.toMap(Header::getName, Header::getValue));

            T responseBody = response.as(responseType);

            return new ApiResponseWrapper<>(statusCode, headers, responseBody);


        } catch (Exception e) {
            // Handle errors with informative messages
            throw new RuntimeException("Failed to deserialize the API response for class: " +
                    responseType.getSimpleName() + ". Error: " + e.getMessage() +
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