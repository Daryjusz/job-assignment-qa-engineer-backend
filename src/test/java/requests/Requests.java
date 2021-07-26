package requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Requests {
    static String host = "http://localhost:3000";

    public static Response doGet(String url) {
        return given().header("Authorization", String.format("Token %s", null)).when().get(host + url);
    }

    public static Response doGet(String url, String auth) {
        return given().header("Authorization", String.format("Token %s", auth)).when().get(host + url);
    }

    public static Response doPost(String url, String body) {
        return given().header("Authorization", String.format("Token %s", null)).contentType(ContentType.JSON).body(body).when().post(host + url);
    }

    public static Response doPost(String url, String body, String auth) {
        return given().header("Authorization", String.format("Token %s", auth)).contentType(ContentType.JSON).body(body).when().post(host + url);
    }

    public static Response doPut(String url, String body, String auth) {
        return given().header("Authorization", String.format("Token %s", auth)).contentType(ContentType.JSON).body(body).when().put(host + url);
    }

    public static Response doDelete(String url, String auth) {
        return given().header("Authorization", String.format("Token %s", auth)).when().delete(host + url);
    }
}
