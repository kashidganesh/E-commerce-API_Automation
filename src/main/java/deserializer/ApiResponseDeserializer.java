package deserializer;

import io.restassured.response.Response;

import java.util.Map;
import java.util.stream.Collectors;

public class ApiResponseDeserializer<T> {

    private final Class<T> responseClass;

    // Constructor that accepts the class type for deserialization
    public ApiResponseDeserializer(Class<T> responseClass) {
        this.responseClass = responseClass;
    }
        // Method to handle the complete deserialization process
        public ApiResponseWrapper<T> deserializeResponse (Response response){
            // Deserialize the body
            T responseBody = response.as(responseClass);

            // Get status code and headers
            int statusCode = response.getStatusCode();
            Map<String, String> headers = response.getHeaders().asList().stream()
                    .collect(Collectors.toMap(header -> header.getName(), header -> header.getValue()));

            // Wrap all parts of the response
            return new ApiResponseWrapper<>(statusCode, headers, responseBody);
        }

}