import entities.Profile;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import static requests.Requests.*;

public class ProfileTests extends BaseTest {

    @Test
    public void shouldGetExistingProfile() {
        Response response = doGet("/api/profiles/alice");
        Profile profile = (response.jsonPath().getObject(".", Profile.class));

        SoftAssertions s = new SoftAssertions();
        s.assertThat(response.statusCode()).isEqualTo(200);
        s.assertThat(profile.getProfile().getBio()).isEqualTo("I am Alice");
        s.assertThat(profile.getProfile().getImage()).isEmpty();
        s.assertThat(profile.getProfile().getUsername()).isEqualTo("alice");
        s.assertThat(profile.getProfile().isFollowing()).isFalse();
        s.assertAll();
    }

    @Test
    public void shouldNotGetNotExistingProfile() {
        Response response = doGet("/api/profiles/notexisting");

        SoftAssertions s = new SoftAssertions();
        s.assertThat(response.statusCode()).isEqualTo(404);
        s.assertThat(response.jsonPath().getString("message")).isEqualTo(NOT_FOUND);
        s.assertAll();
    }

    @Test()
    public void shouldFollowAndUnfollowProfile() {
        Profile profile = doGet("/api/profiles/alice", token()).jsonPath().getObject(".", Profile.class);
        doPost("/api/profiles/alice/follow", "", token());
        Profile profileWithFollowed = doGet("/api/profiles/alice", token()).jsonPath().getObject(".", Profile.class);
        doDelete("/api/profiles/alice/follow", token());
        Profile profileWithUnfollowed = doGet("/api/profiles/alice", token()).jsonPath().getObject(".", Profile.class);

        SoftAssertions s = new SoftAssertions();
        s.assertThat(profile.getProfile().isFollowing()).isFalse();
        s.assertThat(profileWithFollowed.getProfile().isFollowing()).isTrue();
        s.assertThat(profileWithUnfollowed.getProfile().isFollowing()).isFalse();
        s.assertAll();
    }

    @Test()
    public void shouldNotFollowNotExistingProfile() {
        Response response = doPost("/api/profiles/notexsiting/follow", "", token());

        SoftAssertions s = new SoftAssertions();
        s.assertThat(response.statusCode()).isEqualTo(404);
        s.assertThat(response.jsonPath().getString("message")).isEqualTo(NOT_FOUND);
        s.assertAll();
    }


    @Test()
    public void shouldNotUnfollowNotExistingProfile() {
        Response response = doDelete("/api/profiles/notexsiting/follow", token());

        SoftAssertions s = new SoftAssertions();
        s.assertThat(response.statusCode()).isEqualTo(404);
        s.assertThat(response.jsonPath().getString("message")).isEqualTo(NOT_FOUND);
        s.assertAll();
    }

}
