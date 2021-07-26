package entities;

public class Comment {

    Body comment;

    public Comment(Body body) {
        this.comment = body;
    }

    public Body getBody() {
        return comment;
    }


    public static class Body {
        String body;

        public Body(String body) {
            this.body = body;
        }

        public String getBody() {
            return body;
        }
    }
}
