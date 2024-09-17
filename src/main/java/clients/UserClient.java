package clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utilities.PropertyUtils;

import static io.restassured.RestAssured.given;

public class UserClient {

    // Singleton instance of UserClient
    private static UserClient instance;

    private  UserClient() {
        RestAssured.baseURI = PropertyUtils.getProperty("base.url");
    }

    // Method to get the single instance of UserClient
    public static UserClient getInstance() {
        if (instance == null) {
            instance = new UserClient();
        }
        return instance;
    }


    // Method to create a new user (signup)
    public Response createUser(String email, String password) {
        String requestBody = "{\n" +
                "\"email\":\"" + email + "\",\n" +
                "\"password\":\"" + password + "\"\n" +
                "}";

        // Send POST request to /signup endpoint
        return given().contentType(ContentType.JSON).body(requestBody).when().post("/api/auth/signup");
    }

    // Method to authenticate an existing user (login)
    public Response authenticateUser(String email, String password) {
        String requestBody = "{\n" +
                "\"email\":\"" + email + "\",\n" +
                "\"password\":\"" + password + "\"\n" +
                "}";
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/api/auth/login");
    }

    // New method to delete a user by userId
    public Response deleteUser(String userId) {
        return given().when().delete("/api/auth/delete" + userId);
    }

}
