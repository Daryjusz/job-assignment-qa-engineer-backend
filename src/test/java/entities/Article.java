package entities;

import java.util.List;
import java.util.Map;

public class Article {
    ArticleBody article;

    public Article(ArticleBody article) {
        this.article = article;
    }

    public ArticleBody getArticle() {
        return article;
    }

    public static class ArticleBody {
        String slug;
        String title;
        String description;
        String body;
        List<Map<String, Object>> tagList;
        String createdAt;
        String updatedAt;
        boolean favorited;
        int favoritesCount;
        Profile author;

        public ArticleBody(String slug, String title, String description, String body, List<Map<String, Object>> tagList, String createdAt, String updatedAt, boolean favorited, int favoritesCount, Profile author) {
            this.slug = slug;
            this.title = title;
            this.description = description;
            this.body = body;
            this.tagList = tagList;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.favorited = favorited;
            this.favoritesCount = favoritesCount;
            this.author = author;
        }

        public ArticleBody(String title, String description, String body) {
            this.title = title;
            this.description = description;
            this.body = body;
        }

        public String getSlug() {
            return slug;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getBody() {
            return body;
        }

        public List<Map<String, Object>> getTagList() {
            return tagList;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public boolean isFavorited() {
            return favorited;
        }

        public int getFavoritesCount() {
            return favoritesCount;
        }

        public Profile getAuthor() {
            return author;
        }
    }
}
