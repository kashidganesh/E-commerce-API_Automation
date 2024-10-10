package clients;

import deserializer.ApiResponseDeserializer;
import deserializer.ApiResponseWrapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.UserLoginRequest;
import pojos.SignupResponseModel;
import utilities.PropertyUtils;

import static io.restassured.RestAssured.given;

public class UserClient {

    // Singleton instance of UserClient
    private static UserClient instance;
    private final ApiResponseDeserializer<SignupResponseModel> signupDeserializer;
    private  UserClient() {
        signupDeserializer = new ApiResponseDeserializer<>(SignupResponseModel.class);
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
    public ApiResponseWrapper<SignupResponseModel> createUser(String email, String password) {
        String requestBody = "{\n" +
                "\"email\":\"" + email + "\",\n" +
                "\"password\":\"" + password + "\"\n" +
                "}";

        Response response =  given().contentType(ContentType.JSON).body(requestBody).when().post("/api/auth/signup");
      // Use the custom deserializer
        return signupDeserializer.deserializeResponse(response);

     /*   UserSignupRequest signupRequest1 = UserSignupRequest.builder()
                .email(email)
                .password(password)
                .build();

        return given().contentType(ContentType.JSON)
                .body(signupRequest)  // Serialize POJO to JSON here
                .when()
                .post("/api/auth/signup");*/
        // Send POST request to /signup endpoint

    }

    // Method to authenticate an existing user (login)
    public Response authenticateUser(String email, String password) {
    /*    String requestBody = "{\n" +
                "\"email\":\"" + email + "\",\n" +
                "\"password\":\"" + password + "\"\n" +
                "}";
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/api/auth/login");*/

        UserLoginRequest loginRequest = UserLoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        return given().contentType(ContentType.JSON)
                .body(loginRequest) // Serialize POJO to JSON
                .when()
                .post("/api/auth/login");


    }

    // New method to delete a user by userId
    public Response deleteUser(String userId) {
        return given().when().delete("/api/auth/delete/" + userId);
    }

}
