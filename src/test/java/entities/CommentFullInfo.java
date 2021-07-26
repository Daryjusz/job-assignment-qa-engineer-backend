package entities;

public class CommentFullInfo {

    Author author;
    String body;
    String createdAt;
    String id;
    String updatedAt;

    public CommentFullInfo(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public static class Author {
        String bio;
        boolean following;
        String image;
        String username;

        public Author(String bio) {
            this.bio = bio;
        }

        public String getBio() {
            return bio;
        }

        public boolean isFollowing() {
            return following;
        }

        public String getImage() {
            return image;
        }

        public String getUsername() {
            return username;
        }
    }
}
