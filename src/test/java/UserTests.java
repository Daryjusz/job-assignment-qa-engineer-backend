import entities.AuthUser;
import entities.UpdateUser;
import entities.User;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static requests.Requests.*;

public class UserTests extends BaseTest {

    private User user;
    private User secUser;
    private String username = "test";
    private String secUsername = "secUsername";
    private String secEmail = "secEmail@test.com";
    private AuthUser authUserInvalidPassword = new AuthUser(new AuthUser.AuthUserBody(email, "invalid"));
    private AuthUser secAuthUser = new AuthUser(new AuthUser.AuthUserBody(secEmail, password));

    @BeforeTest
    private void prepare() {
        user = new User(new User.UserRegistration(username, email, password));
        secUser = new User(new User.UserRegistration(secUsername, secEmail, password));
        String body = gson.toJson(user);
        String secBody = gson.toJson(secUser);
        doPost("/api/users", body);
        doPost("/api/users", secBody);
    }

    @Test
    public void shouldLogInAsNewUser() {
        String body = gson.toJson(authUser);
        Response response = doPost("/api/users/login", body);

        SoftAssertions s = new SoftAssertions();
        s.assertThat(response.statusCode()).isEqualTo(200);
        s.assertThat(response.jsonPath().getString("user.token")).isNotEmpty();
        s.assertThat(response.jsonPath().getString("user.email")).isEqualTo(authUser.getUser().getEmail());
        s.assertAll();
    }

    @Test
    public void shouldNotLogInWithInvalidPassword() {
        String body = gson.toJson(authUserInvalidPassword);
        Response response = doPost("/api/users/login", body);

        SoftAssertions s = new SoftAssertions();
        s.assertThat(response.statusCode()).isEqualTo(401);
        s.assertThat(response.jsonPath().getString("message")).isEqualTo(UNAUTHORIZED);
        s.assertAll();
    }

    @Test
    public void shouldNotCreateTheSameUserTwice() {
        String body = gson.toJson(user);
        Response response = doPost("/api/users", body);

        SoftAssertions s = new SoftAssertions();
        s.assertThat(response.statusCode()).isEqualTo(409);
        s.assertThat(response.jsonPath().getString("message")).isEqualTo(CONFLICT);
        s.assertAll();
    }

    @Test
    public void shouldGetCurrentUser() {
        Response response = doGet("/api/user", token());

        SoftAssertions s = new SoftAssertions();
        s.assertThat(response.statusCode()).isEqualTo(200);
        s.assertThat(response.jsonPath().getString("user.email")).isEqualTo(authUser.getUser().getEmail());
        s.assertThat(response.jsonPath().getString("user.bio")).isEmpty();
        s.assertThat(response.jsonPath().getString("user.image")).isEmpty();
        s.assertAll();
    }

    @Test(dataProvider = "nullEmptyValuesDataProvider")
    public void shouldNotGetCurrentUserWithoutToken(String val) {
        Response response = doGet("/api/user", val);

        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(response.jsonPath().getString("message")).isEqualTo(UNAUTHORIZED);
    }

    @Test
    public void shouldUpdateUser() {
        Random r = new Random();
        String token = token(secAuthUser);
        Response userBeforeUpdate = doGet("/api/user", token);
        UpdateUser update = new UpdateUser(new UpdateUser.UpdateUserBody("update@email.com" + r.nextInt(100),
                "update", "update", "secUsername" + r.nextInt(10)));
        String body = gson.toJson(update);
        Response updated = doPut("/api/user", body, token);

        SoftAssertions s = new SoftAssertions();
        s.assertThat(updated.statusCode()).isEqualTo(200);
        s.assertThat(updated.jsonPath().getString("user.email")).isNotEqualTo(userBeforeUpdate.jsonPath().getString("user.email"));
        s.assertThat(updated.jsonPath().getString("user.bio")).isNotEqualTo(userBeforeUpdate.jsonPath().getString("user.bio"));
        s.assertThat(updated.jsonPath().getString("user.image")).isNotEqualTo(userBeforeUpdate.jsonPath().getString("user.image"));
        s.assertAll();
    }
}
