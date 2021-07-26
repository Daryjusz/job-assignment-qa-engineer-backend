import com.google.gson.Gson;
import entities.AuthUser;
import org.testng.annotations.*;

import static requests.Requests.doPost;

/*TODO
  add db handling(one of case is deleting user)
  add more specific requests, i.e for getting /api/articles
  create enums for paths
  add more cases for validating input data: nulls/empty values, proper format,size of input, correct/incorrect chars
  refactor base test to contain config information i.e: host, db password, db user
*/
public class BaseTest {

    protected static Gson gson = new Gson();
    protected static final String UNAUTHORIZED = "Unauthorized";
    protected static final String CONFLICT = "Conflict";
    protected static final String NOT_FOUND = "Not Found";

    protected static String email = "test@test.com";
    protected static String password = "test";

    protected static AuthUser authUser = new AuthUser(new AuthUser.AuthUserBody(email, password));

    @AfterSuite
    private void cleanUp() {
        //deleting users
    }


    @DataProvider
    public Object[][] nullEmptyValuesDataProvider() {
        return new Object[][]{{""}, {null}};
    }

    protected static String token() {
        return token(authUser);
    }

    protected static String token(AuthUser authUser) {
        String body = gson.toJson(authUser);
        return doPost("/api/users/login", body).jsonPath().getString("user.token");
    }
}
