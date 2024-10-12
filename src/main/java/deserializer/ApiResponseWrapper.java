package deserializer;

import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.Map;

public abstract class ApiResponseWrapper <T> implements Response {
    private int statusCode;
    private Map<String, String> headers;
    private T responseBody;
    public ApiResponseWrapper(int statusCode, Map<String, String> headers, T responseBody) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.responseBody = responseBody;
    }

    // Getters
    public int getStatusCode() { return statusCode; }
    public Headers getHeaders() { return headers; }
    public T getResponseBody() { return responseBody; }

}
