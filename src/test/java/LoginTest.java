import clients.UserClient;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest extends BaseTest{
    private String uniqueEmail;
    private String password = "12345678";


    @BeforeMethod
    public void setUp() {
        super.beforeMethodSetUp();
        uniqueEmail = "user" + System.currentTimeMillis() + "@example.com";
    }

    @Test
    public void testUserLogin() {

        Response signupResponse = userClient.createUser(uniqueEmail, password);
        signupResponse.then().statusCode(201)
                .body("data.user.id", notNullValue()) // Update path for userId
                .body("data.user.email", equalTo(uniqueEmail)); // Update path for email

        Response loginResponse = userClient.authenticateUser(uniqueEmail, password);
        loginResponse.then().statusCode(200)
                .body("data.session.access_token", notNullValue());
    }


    @Override
    protected void customTearDown() {

    }
}



