import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import static requests.Requests.doGet;

public class TagsTest extends BaseTest {

    //TODO compare with db, check if number of tags is updated after adding or deleting tag
    @Test
    public void shouldGetTags() {
        Response response = doGet("/api/tags");

        SoftAssertions s = new SoftAssertions();
        s.assertThat(response.statusCode()).isEqualTo(200);
        s.assertThat(response.jsonPath().getList("tags").size()).isGreaterThan(1);
        s.assertAll();
    }
}
