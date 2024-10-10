package deserializer;

import java.util.Map;

public class ApiResponseWrapper <T>{
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
    public Map<String, String> getHeaders() { return headers; }
    public T getResponseBody() { return responseBody; }

}
