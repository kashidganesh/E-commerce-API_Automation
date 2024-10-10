package pojos;

import java.util.Map;

public class SignupResponseModel {

    private String userId;
    private String token;
    private int statusCode;
    private Map<String, String> headers;

    // Getters and Setters for userId and token
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    // Getters and Setters for statusCode and headers
    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public Map<String, String> getHeaders() { return headers; }
    public void setHeaders(Map<String, String> headers) { this.headers = headers; }

}
