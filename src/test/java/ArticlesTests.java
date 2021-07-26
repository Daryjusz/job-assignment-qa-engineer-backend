import entities.Article;
import entities.Comment;
import entities.CommentFullInfo;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static requests.Requests.*;

public class ArticlesTests extends BaseTest {

    public static final Article MAIN_ARTICLE = new Article(new Article.ArticleBody("title", "descp", "body"));
    public static final Article UPDATED_ARTICLE = new Article(new Article.ArticleBody("updated", "updated", "updated"));

    @Test

    public void shouldGetListOfArticles() {
        List<Object> response = doGet("/api/articles").jsonPath().getList("articles");

        assertThat(response.size()).isGreaterThan(0);
    }

    @Test
    public void shouldGetSingleArticle() {
        Article article = doGet("/api/articles/zadie-smith-in-defense-of-fiction").jsonPath().getObject(".", Article.class);

        assertThat(article.getArticle().getSlug()).isEqualTo("zadie-smith-in-defense-of-fiction");
    }

    @Test(description = "After creating new article is not visible on all articles list")
    public void shouldCreateAndDeleteArticle() {
        // failing scenario
        int initialSize = doGet("/api/articles").jsonPath().getList("articles").size();
        String articleBody = gson.toJson(MAIN_ARTICLE);
        Response response = doPost("/api/articles", articleBody, token());
        int afterCreatingArticle = doGet("/api/articles").jsonPath().getList("articles").size();
        Article article = doGet("/api/articles/title").jsonPath().getObject(".", Article.class);
        Response deleteResponse = doDelete("/api/articles/title", token());
        int afterDelete = doGet("/api/articles").jsonPath().getList("articles").size();

        SoftAssertions s = new SoftAssertions();
        s.assertThat(response.statusCode()).isEqualTo(201);
        s.assertThat(deleteResponse.statusCode()).isEqualTo(204);
        s.assertThat(afterCreatingArticle).as("After create").isEqualTo(initialSize + 1);
        s.assertThat(afterDelete).as("After delete").isEqualTo(initialSize);
        s.assertThat(article.getArticle().getTitle()).as("Articles title").isEqualTo("title");
        s.assertAll();
    }

    @Test
    public void shouldUpdateArticle() {
        String articleBody = gson.toJson(MAIN_ARTICLE);
        String updated = gson.toJson(UPDATED_ARTICLE);
        doPost("/api/articles", articleBody, token());
        Article initArticle = doGet("/api/articles/title").jsonPath().getObject(".", Article.class);
        doPut("/api/articles/title", updated, token());
        Response responseForInitSlug = doGet("/api/articles/title");
        Article updatedArticle = doGet("/api/articles/updated").jsonPath().getObject(".", Article.class);
        doDelete("/api/articles/updated", token());

        SoftAssertions s = new SoftAssertions();
        s.assertThat(updatedArticle.getArticle().getTitle()).as("Title compare to init").isNotEqualTo("title");
        s.assertThat(updatedArticle.getArticle().getTitle()).as("Title").isEqualTo("updated");
        s.assertThat(updatedArticle.getArticle().getDescription()).as("Description").isEqualTo("updated");
        s.assertThat(updatedArticle.getArticle().getBody()).as("Body").isEqualTo("updated");
        s.assertThat(responseForInitSlug.statusCode()).as("Init slug should return 404").isEqualTo(404);
        s.assertAll();
    }

    @Test
    public void shouldReturnZeroIfThereAreNoCommentsForArticle() {
        Response response = doGet("/api/articles/zadie-smith-in-defense-of-fiction/comments");

        SoftAssertions s = new SoftAssertions();
        s.assertThat(response.statusCode()).isEqualTo(200);
        s.assertThat(response.jsonPath().getList("comments").size()).isZero();
        s.assertAll();
    }

    @Test
    public void shouldCreateAndDeleteComment() {
        Comment commentBody = new Comment(new Comment.Body("test comment"));
        String comment = gson.toJson(commentBody);
        Response addCommentResponse = doPost("/api/articles/zadie-smith-in-defense-of-fiction/comments", comment, token());
        List<CommentFullInfo> comments = doGet("/api/articles/zadie-smith-in-defense-of-fiction/comments", token()).jsonPath().getList("comments", CommentFullInfo.class);
        doDelete(String.format("/api/articles/zadie-smith-in-defense-of-fiction/comments/%s", comments.get(0).getId()), token());

        SoftAssertions s = new SoftAssertions();
        s.assertThat(addCommentResponse.statusCode()).isEqualTo(200);
        s.assertThat(comments.size()).isOne();
        s.assertThat(comments.get(0).getBody()).isEqualTo("test comment");
        s.assertAll();
    }

    @Test
    public void shouldFavoriteAndUnfavoriteArticle() {
        Article article = doGet("/api/articles/zadie-smith-in-defense-of-fiction/", token()).jsonPath().getObject("", Article.class);
        doPost("/api/articles/zadie-smith-in-defense-of-fiction/favorite", "", token()).jsonPath().getObject("", Article.class);
        Article afterFavorite = doGet("/api/articles/zadie-smith-in-defense-of-fiction/", token()).jsonPath().getObject("", Article.class);

        doDelete("/api/articles/zadie-smith-in-defense-of-fiction/favorite", token());
        Article afterUnfavorite = doGet("/api/articles/zadie-smith-in-defense-of-fiction/", token()).jsonPath().getObject("", Article.class);

        SoftAssertions s = new SoftAssertions();
        s.assertThat(article.getArticle().isFavorited()).isFalse();
        s.assertThat(afterFavorite.getArticle().isFavorited()).isTrue();
        s.assertThat(afterUnfavorite.getArticle().isFavorited()).isFalse();
        s.assertAll();
    }
}
