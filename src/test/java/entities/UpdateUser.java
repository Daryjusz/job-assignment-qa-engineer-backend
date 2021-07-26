package entities;

public class UpdateUser {
    UpdateUserBody user;

    public UpdateUser(UpdateUserBody user) {
        this.user = user;
    }

    public UpdateUserBody getUser() {
        return user;
    }

    public static class UpdateUserBody {
        String email;
        String bio;
        String image;
        String username;

        public UpdateUserBody(String email, String bio, String image, String username) {
            this.email = email;
            this.bio = bio;
            this.image = image;
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public String getBio() {
            return bio;
        }

        public String getImage() {
            return image;
        }

        public String getUsername() {
            return username;
        }
    }
}
